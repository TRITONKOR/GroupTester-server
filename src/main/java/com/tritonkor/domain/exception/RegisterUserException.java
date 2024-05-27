package com.tritonkor.domain.exception;

public class RegisterUserException extends RuntimeException{

    public RegisterUserException() {super("Не вдалось зберегти користувача");}
}
