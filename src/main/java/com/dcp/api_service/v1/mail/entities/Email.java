package com.dcp.api_service.v1.mail.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@Setter
public class Email {

	private String to;
	private String from;
	private String subject;
	private String html;

	public Email() {}

	public Email(
		String to,
		String from,
		String subject,
		String html) {
		this.to = to;
		this.from = from;
		this.subject = subject;
		this.html = html;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
