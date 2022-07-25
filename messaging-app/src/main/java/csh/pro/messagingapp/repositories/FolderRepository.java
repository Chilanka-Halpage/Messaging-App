package csh.pro.messagingapp.repositories;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import csh.pro.messagingapp.models.Folder;

@Repository
public interface FolderRepository extends CassandraRepository<Folder, String> {

    List<Folder> getFoldersByUserID(String userID);
}
