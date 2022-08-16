package csh.pro.messagingapp.controllers;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import csh.pro.messagingapp.models.Folder;
import csh.pro.messagingapp.models.MessageList;
import csh.pro.messagingapp.repositories.FolderRepository;
import csh.pro.messagingapp.repositories.MessageListRepository;
import csh.pro.messagingapp.services.FolderService;

@Controller
public class MessageController {

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    FolderService folderService;

    @Autowired
    MessageListRepository messageListRepository;
    
    PrettyTime prettyTime = new PrettyTime();

    @GetMapping(value = "/")
    public String getHomePage(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal == null || !StringUtils.hasText(principal.getAttribute("login")))
            return "index";

        String userID = principal.getAttribute("login");

        // Retrieve Folder
        List<Folder> userFolders = folderRepository.getFoldersByUserID(userID);
        model.addAttribute("userFolders", userFolders);

        List<Folder> defaultFolders = folderService.getDefaultFoldersList(userID);
        model.addAttribute("defaultFolders", defaultFolders);

        // Retrieve Messages
        String label = "Received";
        List<MessageList> receivedMessages = messageListRepository.getMessageListByKeyUserIDAndKeyLabel(userID, label);
        receivedMessages.forEach(message -> {
            Date createdDate = new Date(Uuids.unixTimestamp(message.getKey().getCreatedTimeUuid()));
            message.setAgoTime(prettyTime.format(createdDate));
        });
        model.addAttribute("receivedMessages", receivedMessages);

        return "message-page";
    }
}
