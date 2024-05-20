package org.xproce.moto;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.xproce.moto.DTO.MotorcycleDTO;
import org.xproce.moto.dao.entities.Motorcycle;
import org.xproce.moto.dao.entities.Review;
import org.xproce.moto.dao.entities.WebUser;
import org.xproce.moto.dao.repositories.MotorcycleRepository;
import org.xproce.moto.dao.repositories.WebUserRepository;
import org.xproce.moto.metier.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/motorcycles")
public class MotorcycleController {

    @Autowired
    private MotorcycleManager motorcycleService;

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    @Autowired
    private MotorcycleManager motorcycleManager;

    @Autowired
    WebUserRepository webUserRepository;

    @Autowired
    private ReviewManager reviewService;

    public MotorcycleController(MotorcycleManager motorcycleService) {
        this.motorcycleService = motorcycleService;
    }

    @GetMapping({"", "/"})
    public String showMotorcyclesList(Model model,
                                      @RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "taille", defaultValue = "9") int taille,
                                      @RequestParam(name = "search", defaultValue = "") String name) {
        Page<Motorcycle> motorcycles = motorcycleManager.searchMotorcycles(name, page, taille);
        model.addAttribute("motorcycles", motorcycles.getContent());
        int[] pages = new int[motorcycles.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("keyword", name);
        model.addAttribute("page", page);
        return "motorcycles/index";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String showCreatePage(Model model) {
        MotorcycleDTO motorcycleDTO = new MotorcycleDTO();
        model.addAttribute("motorcycleDTO", motorcycleDTO);
        return "motorcycles/createMotorcycle";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute MotorcycleDTO motorcycleDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "motorcycles/createMotorcycle";
        }

        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setName(motorcycleDTO.getName());
        motorcycle.setBrand(motorcycleDTO.getBrand());
        motorcycle.setImageFileName(motorcycleDTO.getImageUrl());
        motorcycle.setCategory(motorcycleDTO.getCategory());
        motorcycle.setPrice(motorcycleDTO.getPrice());

        motorcycleService.save(motorcycle);

        return "redirect:/motorcycles";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam Long id) {
        try {
            Motorcycle motorcycle = motorcycleService.findById(id);
            if (motorcycle == null) {
                return "redirect:/motorcycles";
            }

            model.addAttribute("motorcycle", motorcycle);

            MotorcycleDTO motorcycleDTO = new MotorcycleDTO();
            motorcycleDTO.setName(motorcycle.getName());
            motorcycleDTO.setBrand(motorcycle.getBrand());
            motorcycleDTO.setImageUrl(motorcycle.getImageFileName());
            motorcycleDTO.setCategory(motorcycle.getCategory());
            motorcycleDTO.setPrice(motorcycle.getPrice());
            model.addAttribute("motorcycleDTO", motorcycleDTO);
        } catch (Exception ex) {
            System.out.println("Exception" + ex.getMessage());
            return "redirect:/motorcycles";
        }

        return "motorcycles/editMotorcycle";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit")
    public String editProduct(Model model, @RequestParam Long id, @Valid @ModelAttribute MotorcycleDTO motorcycleDTO, BindingResult result) {
        try {
            Motorcycle motorcycle = motorcycleService.findById(id);
            if (motorcycle == null) {
                return "redirect:/motorcycles";
            }

            model.addAttribute("motorcycle", motorcycle);

            if (result.hasErrors()) {
                return "motorcycles/editMotorcycle";
            }

            motorcycle.setName(motorcycleDTO.getName());
            motorcycle.setBrand(motorcycleDTO.getBrand());
            motorcycle.setImageFileName(motorcycleDTO.getImageUrl());
            motorcycle.setCategory(motorcycleDTO.getCategory());
            motorcycle.setPrice(motorcycleDTO.getPrice());
            motorcycleService.save(motorcycle);

        } catch (Exception ex) {
            System.out.println("Exception" + ex.getMessage());
        }

        return "redirect:/motorcycles";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete")
    public String deleteProduct(@RequestParam Long id) {
        try {
            Motorcycle motorcycle = motorcycleService.findById(id);
            if (motorcycle == null) {
                return "redirect:/motorcycles";
            }
            motorcycleService.delete(motorcycle);
        } catch (Exception ex) {
            System.out.println("Exception" + ex.getMessage());
        }
        return "redirect:/motorcycles";
    }

    @GetMapping("/motorcycles")
    public String getMotorcycles(Model model) {
        List<Motorcycle> motorcycles = motorcycleService.findAll();
        model.addAttribute("motorcycles", motorcycles);
        return "motorcycles";
    }


    @GetMapping("/review")
    public String showReviewPage(@RequestParam("id") Long id, Model model) {
        Motorcycle motorcycle = motorcycleRepository.findById(id).orElse(null);
        if (motorcycle == null) {
            return "motorcycles/error404";
        }
        model.addAttribute("motorcycle", motorcycle);
        model.addAttribute("reviews", reviewService.findByMotorcycle(motorcycle));
        model.addAttribute("newReview", new Review());
        return "motorcycles/review";
    }

    @PostMapping("/review")
    public String addReview(@Valid @ModelAttribute("newReview") Review review,
                            BindingResult result,
                            @RequestParam("motorcycleId") Long motorcycleId
    ) {
        if (result.hasErrors()) {
            return "motorcycles/review";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        Optional<WebUser> username = webUserRepository.findByUsername(name);

        review.setWebUser(username.get());

        Motorcycle motorcycle = motorcycleRepository.findById(motorcycleId).orElse(null);
        if (motorcycle == null) {
            return "motorcycles/error404";
        }
        review.setMotorcycle(motorcycle);
        review.setDate(LocalDateTime.now());
        reviewService.saveReview(review);
        return "redirect:/motorcycles/review?id=" + motorcycleId;
    }


    @PostMapping("/compare")
    public String compareMotorcycles(@RequestParam("motorcycleIds") List<Long> motorcycleIds, Model model) {
        if (motorcycleIds.size() < 1 || motorcycleIds.size() > 3) {
            return "redirect:/motorcycles";
        }
        List<Motorcycle> motorcycles_list = motorcycleService.findByIds(motorcycleIds);
        model.addAttribute("motorcycles", motorcycles_list);
        return "motorcycles/compare";
    }
}
