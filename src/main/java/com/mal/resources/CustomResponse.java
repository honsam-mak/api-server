package com.mal.resources;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.annotation.JsonGetter;

public class CustomResponse {

	public static class Builder {
		private Status status = Status.OK;
		private String message = "";
		private int code = -1;
		private String moreInfo = "";

		public Builder setStatus(Status status) {
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

		public Response build() {
			return Response.status(status)
				.entity(new CustomResponse(status.getStatusCode(), message, code, moreInfo))
				.build();
		}
	}

	CustomResponse(Integer status, String message, Integer code, String moreInfo) {
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

