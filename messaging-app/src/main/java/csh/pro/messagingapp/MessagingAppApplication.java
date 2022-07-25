package csh.pro.messagingapp;

import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import csh.pro.messagingapp.config.DataStaxAstraProperties;
import csh.pro.messagingapp.models.Folder;
import csh.pro.messagingapp.repositories.FolderRepository;

@SpringBootApplication
public class MessagingAppApplication {

	@Autowired
	private FolderRepository folderRepository;

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
	}
}
