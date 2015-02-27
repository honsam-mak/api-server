package com.mal.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonGetter;

public class CustomException extends WebApplicationException {
	private static final long serialVersionUID = 1L;

	private final int status;
	private final String message;
	private final int code;
	private final String moreInfo;

	public static class Builder {
		//Required
		private final int httpStatus;
		//Optional
		private int status = 0;
		private String message = "";
		private int code = -1;
		private String moreInfo = "";

		public Builder(int httpStatus) {
			this.httpStatus = httpStatus;
			this.status = httpStatus;
		}

		public Builder setStatus(int status) {
			this.status = status;
			return this;
		}
		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}
		public Builder setCode(int code) {
			this.code = code;
			return this;
		}
		public Builder setMoreInfo(String info) {
			this.moreInfo = info;
			return this;
		}

		public CustomException build() {
			return new CustomException(this);
		}
	}

	private CustomException(Builder builder) {
		super(builder.httpStatus);
		status = builder.status;
		message = builder.message;
		code = builder.code;
		moreInfo = builder.moreInfo;
	}

	@Override
	public Response getResponse()
	{
		ResponseBean responseBean = new ResponseBean(status, message, code, moreInfo);

		return Response.fromResponse(super.getResponse())
				.entity(responseBean).build();
	}

	class ResponseBean {
		ResponseBean() {};

		ResponseBean(Integer status, String message, Integer code, String moreInfo) {
			this.status = status;
			this.message = message;
			this.code = code;
			this.moreInfo = moreInfo;
		}

		private Integer status;
		private String message;
		private Integer code;
		private String moreInfo;

		@JsonGetter("status")
		public Integer getStatus() {
			return status;
		}

		@JsonGetter("message")
		public String getMessage() {
			return message;
		}

		@JsonGetter("code")
		public Integer getCode() {
			return code;
		}

		@JsonGetter("more_info")
		public String getMoreInfo() {
			return moreInfo;
		}
	}
}

