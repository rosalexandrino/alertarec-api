package util;

public class ResponseMessage {

	public enum ResponseStatus {
		SUCESSO, FALHA;
	}
	
	private ResponseStatus status;
	
	private String mensagem;
	
	private Object info;

	public ResponseMessage() {
	}
	
	public ResponseMessage(ResponseStatus status, String mensagem, Object info) {
		this.status = status;
		this.mensagem = mensagem;
		this.info = info;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}
}