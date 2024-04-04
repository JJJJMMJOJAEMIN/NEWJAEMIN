package com.example.login.controller;

import com.example.login.Config.AdminAuthorize;
import com.example.login.Config.UserAuthorize;
import com.example.login.domain.Member;
import com.example.login.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/join")
    public String joinPage() {
        return "join";
    }

    @GetMapping("/dashboard")
    public String dashboardPage(@AuthenticationPrincipal User user, Model model)   {

        model.addAttribute("loginId", user.getUsername());
        model.addAttribute("loginRoles", user.getAuthorities());


        return "dashboard";

    }

    @GetMapping("/m_list")
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

    @GetMapping("/m_view/{idx}")
    public String memberView(Model model, @PathVariable(name="idx") Long idx) {

        Member result = memberService.boardView(idx);

        model.addAttribute("member", result);
        return "m_view";
    }
    @GetMapping("/setting/admin")
    @AdminAuthorize
    public String adminSettingPage() {
        return "admin_setting";
    }

    @GetMapping("/setting/user")
    @UserAuthorize
    public String userSettingPage() {
        return "user_setting";
    }
}

//길라잡이