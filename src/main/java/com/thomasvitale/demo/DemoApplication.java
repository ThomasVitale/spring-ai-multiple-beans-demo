package com.thomasvitale.demo;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import com.thomasvitale.demo.autoconfigure.ModelName;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

@RestController
class ChatController {

	private final OllamaChatModel chatModel;
	private final OllamaChatModel agentModel;
	private final OllamaChatModel ragModel;

	public ChatController(OllamaChatModel chatModel,
						  @ModelName("agent") OllamaChatModel agentModel,
						  @ModelName("rag") OllamaChatModel ragModel
	) {
		this.chatModel = chatModel;
		this.agentModel = agentModel;
		this.ragModel = ragModel;
	}

	@GetMapping("/chat")
	public String chat(@RequestParam("question") String question) {
		return chatModel.call(question);
	}

	@GetMapping("/chat/agent")
	public String agent(@RequestParam("question") String question) {
		return agentModel.call(question);
	}

	@GetMapping("/chat/rag")
	public String rag(@RequestParam("question") String question) {
		return ragModel.call(question);
	}
	
}
