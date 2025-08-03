package com.thomasvitale.demo.autoconfigure.api;

import org.springframework.boot.autoconfigure.service.connection.ConnectionDetails;

public interface OllamaConnectionDetails extends ConnectionDetails {

	String getBaseUrl();

}
