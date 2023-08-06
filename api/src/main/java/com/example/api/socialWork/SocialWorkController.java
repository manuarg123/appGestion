package com.example.api.socialWork;

import com.example.api.common.APIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/socialWorks")
public class SocialWorkController {
    private final SocialWorkService socialWorkService;

    public SocialWorkController(SocialWorkService socialWorkService) {
        this.socialWorkService = socialWorkService;
    }

    @GetMapping
    public List<SocialWork> getSocialWorks() {
        return this.socialWorkService.geSocialWorks();
    }

    @GetMapping(path = "/show/{socialWorkId}")
    public APIResponse getSocialWork(@PathVariable("socialWorkId") Long id) {
        return this.socialWorkService.getSocialWork(id);
    }

    @PostMapping(path = "/new")
    public APIResponse addSocialWork(@RequestBody @Valid SocialWorkDTO socialWorkDTO) {
        return this.socialWorkService.newSocialWork(socialWorkDTO);
    }

    @PutMapping(path = "/edit/{socialWorkId}")
    public APIResponse editSocialWork(@PathVariable("socialWorkId") Long id, @RequestBody @Valid SocialWorkDTO socialWorkDTO) {
        return this.socialWorkService.editSocialWork(id, socialWorkDTO);
    }

    @DeleteMapping(path = "/delete/{socialWorkId}")
    public APIResponse deleteSocialWork(@PathVariable("socialWorkId") Long id) {
        return this.socialWorkService.deleteSocialWork(id);
    }

    @GetMapping(path = "/paginated")
    public Page<SocialWork> getSocialWorksPaginated(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        return socialWorkService.getSocialWorksPaginated(currentPage, pageSize);
    }
}
