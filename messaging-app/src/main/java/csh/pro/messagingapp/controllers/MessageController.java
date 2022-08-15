package csh.pro.messagingapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import csh.pro.messagingapp.models.Folder;
import csh.pro.messagingapp.repositories.FolderRepository;
import csh.pro.messagingapp.services.FolderService;

@Controller
public class MessageController {

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    FolderService folderService;

    @GetMapping(value = "/")
    public String getHomePage(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal == null || !StringUtils.hasText(principal.getAttribute("login")))
            return "index";

        String userID = principal.getAttribute("login");

        List<Folder> userFolders = folderRepository.getFoldersByUserID(userID);
        model.addAttribute("userFolders", userFolders);

        List<Folder> defaultFolders = folderService.getDefaultFoldersList(userID);
        model.addAttribute("defaultFolders", defaultFolders);

        return "message-page";
    }
}
