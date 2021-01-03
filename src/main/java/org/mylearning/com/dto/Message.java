package org.mylearning.com.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.Objects;

@RegisterForReflection
public class Message {
    private String message;

    public Message(){
        this.message="Hello World";
    }

    public Message(String message){
        this.message=message;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.message);
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
