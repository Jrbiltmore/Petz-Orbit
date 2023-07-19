package ru.nsu.sberlab.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.nsu.sberlab.exceptions.FailedUserCreationException;
import ru.nsu.sberlab.exceptions.NotFoundException;
import ru.nsu.sberlab.exceptions.UnauthorizedAccessException;

@ControllerAdvice
@RequiredArgsConstructor
public class ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(FailedUserCreationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleFailedCreationException(Model model, FailedUserCreationException ex) {
        logger.error("Failed to create user: {}", ex.getMessage());
        model.addAttribute("errorMessage", "Failed to create user: " + ex.getMessage());
        return "failed-registration";
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(Model model, NotFoundException ex) {
        logger.error("Resource not found: {}", ex.getMessage());
        model.addAttribute("errorMessage", "Resource not found: " + ex.getMessage());
        return "not-found";
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorizedAccessException(Model model, UnauthorizedAccessException ex) {
        logger.error("Unauthorized access: {}", ex.getMessage());
        model.addAttribute("errorMessage", "Unauthorized access: " + ex.getMessage());
        return "unauthorized";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Model model, Exception ex) {
        logger.error("An unexpected error occurred:", ex);
        model.addAttribute("errorMessage", "Internal Server Error: " + ex.getMessage());
        return "error-message";
    }

    @Controller
    public static class ErrorViewController {

        @GetMapping("/error")
        public String handleGenericError(Model model) {
            model.addAttribute("errorMessage", "Oops! Something went wrong. Please try again.");
            return "error";
        }

        @GetMapping("/error/access-denied")
        public String handleAccessDeniedError(Model model) {
            model.addAttribute("errorMessage", "Access Denied. You are not authorized to access this page.");
            return "access-denied-error";
        }

        @GetMapping("/error/waze")
        public String handleWazeError(Model model) {
            model.addAttribute("errorMessage", "Waze navigation failed. Please use an alternative navigation app.");
            return "waze-error";
        }

        @GetMapping("/error/google-maps")
        public String handleGoogleMapsError(Model model) {
            model.addAttribute("errorMessage", "Google Maps navigation failed. Please use an alternative navigation app.");
            return "google-maps-error";
        }

        @GetMapping("/error/shopify")
        public String handleShopifyError(Model model) {
            model.addAttribute("errorMessage", "Failed to connect to Shopify. Please try again later.");
            return "shopify-error";
        }

        @GetMapping("/error/instagram")
        public String handleInstagramError(Model model) {
            model.addAttribute("errorMessage", "Failed to connect to Instagram. Please try again later.");
            return "instagram-error";
        }

        @GetMapping("/error/meta")
        public String handleMetaError(Model model) {
            model.addAttribute("errorMessage", "Failed to retrieve meta information. Please try again later.");
            return "meta-error";
        }
        
        @ExceptionHandler(Exception.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public String handleException(Model model, Exception ex) {
            logger.error("An unexpected error occurred:", ex);
            model.addAttribute("errorMessage", "Internal Server Error: " + ex.getMessage());
            return "error-message";
        }
    }
}
