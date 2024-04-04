
package com.example.login.controller;

import com.example.login.domain.Member;
import com.example.login.service.MemberService;
import com.example.login.service.RegisterMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor

public class MemberController {

    private final RegisterMemberService registerMemberService;
    private final MemberService memberService;


    //리스트
    @GetMapping("/member/m_list")
    public String memberList(Model model, @PageableDefault(page = 0, size = 10, sort = "idx", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Member> list = memberService.viewList(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("dataList", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "m_list";
    }
    
    //삭제
    @GetMapping("/member/m_delete")
    public String memberDelete(@RequestParam(name="idx") Long idx){

        memberService.MemberDelete(idx);
        return "redirect:/member/m_list";
    }
    @GetMapping("/member/m_modify/{idx}")
    public String memberModify(@PathVariable("idx")Long idx, Model model){

        model.addAttribute("member", memberService.boardView(idx));
        return "m_modify";
    }

    //수정


}

