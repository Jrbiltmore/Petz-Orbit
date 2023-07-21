package ru.nsu.sberlab.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.instagram.api.Instagram;
import org.springframework.social.instagram.api.Media;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.sberlab.services.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.nsu.sberlab.models.entities.User;

@Controller
@RequestMapping("/pet/")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;
    private final Facebook facebook; // Assuming you've configured Facebook integration bean
    private final Instagram instagram; // Assuming you've configured Instagram integration bean

    @GetMapping("add-new-pet")
    public String petCreationPage() {
        return "pet-creation";
    }

    @GetMapping("privileged-list")
    public String privilegedPetsList(
            Model model,
            @PageableDefault Pageable pageable,
            @AuthenticationPrincipal User principal
    ) {
        // Get Facebook and Instagram data for the currently authenticated user
        User loggedInUser = principal;
        String facebookData = getFacebookData(loggedInUser);
        String instagramData = getInstagramData(loggedInUser);

        // Add the data to the model
        model.addAttribute("facebookData", facebookData);
        model.addAttribute("instagramData", instagramData);

        // Fetch privileged pets list and add it to the model
        model.addAttribute("pets", petService.petsList(pageable));

        // Add the currently authenticated user to the model
        model.addAttribute("user", principal);
        return "pets-privileged-list";
    }

    @GetMapping("find")
    public String findPetInfo(
            Model model,
            @RequestParam(name = "chipId", required = false) String chipId
    ) {
        model.addAttribute("pet", petService.getPetByChipId(chipId));
        return "pet-info";
    }

    // Implement NIST-level security methods here
    // ...

    // Helper method to get Facebook data for the currently authenticated user
    private String getFacebookData(User user) {
        if (user != null && user.getFacebookAccessToken() != null) {
            // Assuming you've implemented a method to retrieve Facebook data using the access token
            String facebookData = fetchFacebookData(user.getFacebookAccessToken());
            return facebookData;
        }
        return "No Facebook data available.";
    }

    // Helper method to get Instagram data for the currently authenticated user
    private String getInstagramData(User user) {
        if (user != null && user.getInstagramAccessToken() != null) {
            // Assuming you've implemented a method to retrieve Instagram data using the access token
            String instagramData = fetchInstagramData(user.getInstagramAccessToken());
            return instagramData;
        }
        return "No Instagram data available.";
    }

    // Helper method to fetch Facebook data using the access token
    private String fetchFacebookData(String accessToken) {
        // Assuming you've integrated with Facebook API to retrieve data
        // For example, you can fetch recent posts from the user's feed
        StringBuilder result = new StringBuilder();
        for (Post post : facebook.feedOperations().getFeed()) {
            result.append("Post: ").append(post.getMessage()).append("\n");
        }
        return result.toString();
    }

    // Helper method to fetch Instagram data using the access token
    private String fetchInstagramData(String accessToken) {
        // Assuming you've integrated with Instagram API to retrieve data
        // For example, you can fetch recent media from the user's feed
        StringBuilder result = new StringBuilder();
        for (Media media : instagram.mediaOperations().getRecentMedia()) {
            result.append("Media: ").append(media.getLink()).append("\n");
        }
        return result.toString();
    }
}