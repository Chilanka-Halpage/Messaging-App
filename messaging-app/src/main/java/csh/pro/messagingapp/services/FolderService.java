package csh.pro.messagingapp.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import csh.pro.messagingapp.models.Folder;

@Service
public class FolderService {
    
    public List<Folder> getDefaultFoldersList(String userID){
        return Arrays.asList(
            new Folder(userID, "Inbox", "yellow"),
            new Folder(userID, "Outbox", "purple")
        );
    }
}
