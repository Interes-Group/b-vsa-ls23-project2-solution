package sk.stuba.fei.uim.vsa.pr2.model.dto;

import lombok.Data;

import javax.ws.rs.core.Response;

@Data
public class Message {

    private Integer code;
    private String message;
    private Error error;

    public Message() {
    }

    public Message(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Message(Response.Status code, String message) {
        this.code = code.getStatusCode();
        this.message = message;
    }

    public Message(Integer code, String message, Error error) {
        this.code = code;
        this.message = message;
        this.error = error;
    }

    public Message(Response.Status code, String message, Error error) {
        this.code = code.getStatusCode();
        this.message = message;
        this.error = error;
    }

    public static Message errorMessage(Integer code, String message, String type, String trace) {
        return new Message(code, message, new Error(type, trace));
    }

    public static Message errorMessage(Response.Status code, Throwable throwable) {
        String msg = throwable.getMessage();
        if (msg == null || msg.isEmpty()) {
            msg = "[" + code.getReasonPhrase() + "] caused by " + throwable.getClass().getSimpleName();
        }
        return new Message(code.getStatusCode(), msg, new Error(throwable));
    }

    public static Message errorMessage(Response.Status code, String message, String type, StackTraceElement[] stackTrace) {
        return errorMessage(code.getStatusCode(), message, type, Error.serializeStackTrace(stackTrace));
    }

    @Data
    public static class Error {
        private String type;
        private String trace;

        public Error() {
        }

        public Error(String type, String trace) {
            this.type = type;
            this.trace = trace;
        }

        public Error(Throwable throwable) {
            this.type = throwable.getClass().getSimpleName();
            this.trace = serializeStackTrace(throwable.getStackTrace());
        }

        public static String serializeStackTrace(StackTraceElement[] trace) {
            StringBuilder builder = new StringBuilder(trace.length);
            if (trace.length == 0) return builder.toString();
            builder.append(trace[0]);
            for (StackTraceElement ste : trace) {
                builder.append(";\n ");
                builder.append(ste.toString());
            }
            return builder.toString();
        }
    }
}
