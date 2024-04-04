package com.example.login.controller;

import com.example.login.domain.Board;
import com.example.login.domain.Category;
import com.example.login.domain.Member;
import com.example.login.dto.BoardDTO;
import com.example.login.dto.FileDTO;
import com.example.login.repository.BoardRepository;
/*
import com.example.login.repository.CategoryRepository;
*/
import com.example.login.repository.MemberRepository;
import com.example.login.service.BoardService;
import com.example.login.service.CategoryService;
import com.example.login.service.FileService;
import com.example.login.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final MemberRepository memberRepository;
    private final BoardService boardService;
    private final MemberService memberService;
    private final BoardRepository boardRepository;
    private final FileService fileService;
    private final CategoryService categoryService;
/*
    private final CategoryRepository categoryRepository;
*/

    @GetMapping("/board/index")
    public String test(){
        return "index";
    }


    //작성
    @GetMapping("/board/write")
    public String boardWriteForm(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("memberId", user.getUsername());

        return "boardwrite";
    }
    //디비 업로드
    @PostMapping("/board/writepro")
    public String boardWritePro(BoardDTO dto, @RequestParam("files") MultipartFile[] files) throws Exception {
        // Member 엔티티 찾기
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없습니다."));

        // DTO를 Entity로 변환하고 저장
        Board board = dto.toEntity(member);
        boardRepository.save(board);

        for (MultipartFile file : files) {
            if (!file.isEmpty()) { // 파일이 비어있지 않은 경우에만 업로드 처리
                try {
                    fileService.fileWrite(new FileDTO(), file, board.getIdx());
                } catch (Exception e) {
                    e.printStackTrace();
                    // 파일 업로드 중 에러가 발생하면 처리할 로직
                }
            }
        }
        return "redirect:/board/list"; // 혹은 적절한 리다이렉션 주소
    }



    //리스트&페이지&검색
    @GetMapping("/board/list")
    public String memberList(Model model,
                             @RequestParam(name = "searchKeyword", required = false) String searchKeyword,
                             @RequestParam(name = "searchType", required = false, defaultValue = "title") String searchType,
                             @PageableDefault(page = 0, size = 5, sort = "idx",
                                     direction = Sort.Direction.DESC) Pageable pageable) {



        List<Category> categoryList = categoryService.categoryList();
        Page<Board> list = boardService.BoardList(pageable);

        if (searchKeyword == null || searchKeyword.isEmpty()) {
            list = boardService.BoardList(pageable);
        } else {
            list = boardService.boardSearchList(searchKeyword, searchType, pageable);
        }
        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());
        int totalPage = list.getTotalPages();
        //영어 이름 해석 하면 뭐가 뭔지 암
        model.addAttribute("dataList", list.getContent());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("list", list);
        model.addAttribute("category", categoryList);
        //확인 하는거
        model.addAttribute("isFirst", list.isFirst());
        model.addAttribute("isLast", list.isLast());

        return "list";
    }

    //삭제
    @GetMapping("/board/delete/{idx}")
    public String BoardDelete(@PathVariable(value = "idx") Long idx) {
        boardService.BoardDelete(idx);
        fileService.deleteBoardIdx(idx);
        return "redirect:/board/list";
    }
    //수정 폼
    @GetMapping("/board/modify/{idx}")
    public String boardWriteForm(@PathVariable(value = "idx") Long idx, Model model) {
        Board board = boardRepository.findById(idx).orElse(null);
        model.addAttribute("board", board);

        return "boardmodify";
    }

    //수정 처리
    @PostMapping("board/update/{idx}")
    public String memberUpdate(@PathVariable("idx") Long idx,
                               @ModelAttribute BoardDTO dto,
                               @RequestParam(name = "file", required = false) List<MultipartFile> file,
                               @RequestParam(name = "fileIdx", required = false) List<Long> fileIdx) throws Exception{


        System.out.println("파일 리스트 크기: " + (file != null ? file.size() : "null"));

        // 파일 리스트가 null이 아니라면, 각 파일의 이름 출력
        if (file != null) {
            for (MultipartFile multipartFile : file) {
                System.out.println("파일 이름: " + multipartFile.getOriginalFilename());
            }
        }
        boardService.boardUpdate(idx, dto, file,fileIdx); // 파일 리스트를 포함하여 호출
        return "redirect:/board/list";
    }
 /*   @GetMapping("/images")
    public String displayImage(@RequestParam("dir") String dir, Model model,
                               @RequestParam("filename") String filename) {

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

        // 이미지가 실제로 저장되어 있는 경로 조합
        String filePath = projectPath + "/" + dir + "/" + filename;
        Path path = Paths.get(filePath);

        // 파일의 실제 서버 상의 경로를 뷰로 전달
        model.addAttribute("dir", dir);
        model.addAttribute("filename", filename);
        model.addAttribute("filePath", path.toString());

        // 이미지를 보여줄 뷰의 이름을 반환
        return "boardview"; // imageDisplay.html이라는 뷰 템플릿을 사용한다고 가정
    }

*/

/*
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource showImage(@PathVariable String filename) throws MalformedURLException{
        return new UrlResource("file" + file.get)
    }

*/

    @GetMapping("/board/view/{idx}")
    public String BoardView(Model model, @PathVariable(value = "idx") Long idx) {
        //상세페이지
        Board board = boardService.BoardView(idx);

        model.addAttribute("board", board);
        return "boardview";


        /*    @GetMapping("/images")
        public ResponseEntity<Resource> displayImage(@RequestParam("dir") String dir, Model model,
                                                     @RequestParam("filename") String filename) throws IOException {


            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

            System.out.println("----------------------------------------------------------------------");
            System.out.println("dir: " + dir + ", filename: " + filename); // 콘솔에 출력
            System.out.println("----------------------------------------------------------------------");

            String filePath = dir + "/" + filename;
            Path path = Paths.get(filePath);
            Resource resource = new UrlResource(path.toUri());


            model.addAttribute("dir", dir);
            model.addAttribute("path", path);
            // 이미지의 Content-Type 설정
            String contentType = Files.probeContentType(path);
            if (contentType == null || !contentType.startsWith("image")) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        }*/
    }

}