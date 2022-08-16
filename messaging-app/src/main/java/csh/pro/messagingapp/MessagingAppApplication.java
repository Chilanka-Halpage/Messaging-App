package csh.pro.messagingapp;

import java.nio.file.Path;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import csh.pro.messagingapp.config.DataStaxAstraProperties;
import csh.pro.messagingapp.keys.MessageListKey;
import csh.pro.messagingapp.models.MessageList;
import csh.pro.messagingapp.models.Folder;
import csh.pro.messagingapp.repositories.FolderRepository;
import csh.pro.messagingapp.repositories.MessageListRepository;

@SpringBootApplication
public class MessagingAppApplication {

	@Autowired
	private FolderRepository folderRepository;

	@Autowired
	MessageListRepository messageListRepository;

	public static void main(String[] args) {
		SpringApplication.run(MessagingAppApplication.class, args);
	}

	// @RequestMapping("/user")
	// public String user(@AuthenticationPrincipal OAuth2User principal) {
	// System.out.println(principal);
	// return principal.getAttribute("login");
	// }

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}

	@PostConstruct
	public void initialize() {
		folderRepository.save(new Folder("Chilanka-Halpage", "Received", "green"));
		folderRepository.save(new Folder("Chilanka-Halpage", "Sent", "blue"));
		folderRepository.save(new Folder("Chilanka-Halpage", "Important", "red"));

		for (int i = 0; i < 10; i++) {
			MessageListKey key = new MessageListKey();
			key.setUserID("Chilanka-Halpage");
			key.setLabel("Received");
			key.setCreatedTimeUuid(Uuids.timeBased());

			MessageList message = new MessageList();
			message.setKey(key);
			message.setSubject("subject " + (i + 1));
			message.setTo(Arrays.asList("Chilanka-Halpage"));
			message.setRead(false);

			messageListRepository.save(message);
		}
	}
}
