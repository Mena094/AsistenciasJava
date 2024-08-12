package com.ESFE.Asistencias.Controladores;


import com.ESFE.Asistencias.Entidades.Grupo;
import com.ESFE.Asistencias.Servicios.Interfaces.IGrupoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/grupos")
public class GrupoController {
    @Autowired
    private IGrupoServices grupoService;

    @GetMapping
    public String index (Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page. orElse(  1) - 1; // si no está seteado se asigna @
        int pageSize = size. orElse( 5); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize) ;

        Page<Grupo> grupos = grupoService.BuscarTodosPaginados(pageable) ;
        model. addAttribute(  "grupos", grupos) ;
        int totalPages = grupos. getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                .collect(Collectors.toList()) ;
            model. addAttribute( "pageNumbers",pageNumbers);
        }
        return "grupo/index";
    }
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("grupo", new Grupo());
        return "grupo/create";
    }

    @PostMapping("/save")
    public String save(Grupo grupo, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("grupo", grupo);
            attributes.addFlashAttribute("error", "No se puede guardar debido a un error");
            return "grupo/create";
        }
        boolean isEdit = grupo.getId() != null && grupo.getId() > 0;
        grupoService.CreaOeditar(grupo);
        if (isEdit) {
            attributes.addFlashAttribute("msg", "Editado Correctamente");
        } else {
            attributes.addFlashAttribute("msg", "Creado Correctamente");
        }
        return "redirect:/grupos";
    }

    @GetMapping("/details/{id}")
    public String Integer(@PathVariable("id") Integer id, Model model){
        Grupo grupo = grupoService.BuscarporId(id).get();
         model.addAttribute( "grupo", grupo );
        return "grupo/details";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        Grupo grupo = grupoService.BuscarporId(id).get();
        model.addAttribute( "grupo", grupo );
        return "grupo/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove (@PathVariable("id") Integer id, Model model){
        Grupo grupo = grupoService.BuscarporId(id).get();
       model.addAttribute(  "grupo", grupo);
        return "grupo/delete";
    }

    @PostMapping("/delete")
    public String delete(Grupo grupo, RedirectAttributes attributes) {
        grupoService.EliminarPorId(grupo.getId());
        attributes.addFlashAttribute("msg", "Grupo eliminado correctamente");
        return "redirect:/grupos";
    }
}
