package csh.pro.messagingapp.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import csh.pro.messagingapp.models.Folder;
import csh.pro.messagingapp.models.Message;
import csh.pro.messagingapp.repositories.FolderRepository;
import csh.pro.messagingapp.repositories.MessageRepository;
import csh.pro.messagingapp.services.FolderService;

@Controller
public class MessageViewController {

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    FolderService folderService;

    @Autowired
    MessageRepository messageRepository;
    
    @GetMapping(value = "/messages/{id}")
    public String getHomePage(@AuthenticationPrincipal OAuth2User principal, @PathVariable UUID id, Model model) {
        if (principal == null || !StringUtils.hasText(principal.getAttribute("login")))
            return "index";

        String userID = principal.getAttribute("login");

        // Retrieve Folder
        List<Folder> userFolders = folderRepository.getFoldersByUserID(userID);
        model.addAttribute("userFolders", userFolders);

        List<Folder> defaultFolders = folderService.getDefaultFoldersList(userID);
        model.addAttribute("defaultFolders", defaultFolders);

        // Retrieve Message Details by ID
        Optional<Message> optionalMessage = messageRepository.findById(id);

        if(optionalMessage.isEmpty()){
            return "message-page";
        }
        Message message = optionalMessage.get();
        String toList = String.join(",", message.getTo());
        model.addAttribute("message", message);
        model.addAttribute("toList", toList);

        return "message-view";
    }
}
