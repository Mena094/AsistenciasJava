package com.ESFE.Asistencias.Controladores;

import com.ESFE.Asistencias.Entidades.Docente;
import com.ESFE.Asistencias.Entidades.DocenteGrupo;
import com.ESFE.Asistencias.Entidades.Grupo;
import com.ESFE.Asistencias.Servicios.Interfaces.IDocenteGrupoService;
import com.ESFE.Asistencias.Servicios.Interfaces.IDocenteService;
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
@RequestMapping("/docenteGrupos")
public class DocenteGrupoController {
    @Autowired
    private IDocenteGrupoService docenteGrupoService;
    @Autowired
    private IDocenteService docenteService;
    @Autowired
    private IGrupoServices grupoServices;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1) - 1; // si no está seteado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<DocenteGrupo> docenteGrupos = docenteGrupoService.buscarTodosPaginados(pageable);
        model.addAttribute("docenteGrupos", docenteGrupos);

        int totalPages = docenteGrupos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "docenteGrupo/index";
    }
    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("docenteGrupo", new DocenteGrupo());
        model.addAttribute("docentes", docenteService.obtenerTodos());
        model.addAttribute("grupos",grupoServices.ObtenerTodos());
        return "docenteGrupo/create";
    }
    @PostMapping("/save")
    public String save( @RequestParam Integer docenteId,
                        @RequestParam Integer grupoId,
                        @RequestParam Integer anio,
                        @RequestParam String ciclo,
                        RedirectAttributes attributes){
        Docente docente = docenteService.buscarPorId(docenteId).get();
        Grupo grupo = grupoServices.BuscarporId(grupoId).get();

        if(docente != null && grupo != null){
            DocenteGrupo docenteGrupo = new DocenteGrupo();
            docenteGrupo.setDocente(docente);
            docenteGrupo.setGrupo(grupo);
            docenteGrupo.setAnio(anio);
            docenteGrupo.setCiclo(ciclo);
            docenteGrupoService.crearOEditar(docenteGrupo);
            attributes.addFlashAttribute("msg", "Docente grupo creado");
        }
        return "redirect:/docenteGrupos";
    }
    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model){
        DocenteGrupo docenteGrupo = docenteGrupoService.buscarPorId(id).get();
        model.addAttribute("docenteGrupo", docenteGrupo);
        return "docenteGrupo/details";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        DocenteGrupo docenteGrupo = docenteGrupoService.buscarPorId(id).get();
        model.addAttribute("docentes", docenteService.obtenerTodos());
        model.addAttribute("grupos", grupoServices.ObtenerTodos());
        model.addAttribute("docenteGrupo", docenteGrupo);
        return "docenteGrupo/edit";
    }

    @PostMapping("/update")
    public String update(@RequestParam Integer id, @RequestParam Integer docenteId,
                         @RequestParam Integer grupoId,
                         @RequestParam Integer anio, @RequestParam String ciclo,
                         RedirectAttributes attributes){
        Docente docente = docenteService.buscarPorId(docenteId).get();
        Grupo grupo = grupoServices.BuscarporId(grupoId).get();

        if(docente != null && grupo != null){
            DocenteGrupo docenteGrupo = new DocenteGrupo();
            docenteGrupo.setId(id);
            docenteGrupo.setDocente(docente);
            docenteGrupo.setGrupo(grupo);
            docenteGrupo.setAnio(anio);
            docenteGrupo.setCiclo(ciclo);

            docenteGrupoService.crearOEditar(docenteGrupo);
            attributes.addFlashAttribute("msg", "Asignación modificada correctamente");
        }

        return "redirect:/docenteGrupos";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model){
        DocenteGrupo docenteGrupo = docenteGrupoService.buscarPorId(id).get();
        model.addAttribute("docenteGrupo", docenteGrupo);
        return "docenteGrupo/delete";
    }

    @PostMapping("/delete")
    public String delete(DocenteGrupo docenteGrupo, RedirectAttributes attributes){
        docenteGrupoService.eliminarPorId(docenteGrupo.getId());
        attributes.addFlashAttribute("msg", "Asignación eliminada correctamente");
        return "redirect:/docenteGrupos";
    }
}
