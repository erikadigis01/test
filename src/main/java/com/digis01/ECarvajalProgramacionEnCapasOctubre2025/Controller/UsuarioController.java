package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.Controller;

import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO.ColoniaDAOImplementation;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO.EstadoDAOImplementation;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO.MunicipioDAOImplementation;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO.PaisDAOImplementation;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO.UsuarioDAOImplementation;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO.RollDAOImplementation;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO.UsuarioJPADAOImplementation;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Direccion;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.ErrorCarga;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Result;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Roll;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Usuario;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.UsuarioValidator;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.ValidationGroup;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.Service.UsuarioTransaccion;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
//import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;

    @Autowired
    private RollDAOImplementation rollDAOImplementation;

    @Autowired
    private PaisDAOImplementation paisDAOImplementation;

    @Autowired
    private EstadoDAOImplementation estadoImplementation;

    @Autowired
    private ColoniaDAOImplementation coloniaImplementation;

    @Autowired
    private MunicipioDAOImplementation municipioImplementation;

    @Autowired
    private UsuarioValidator usuarioValidator;

    @Autowired
    private UsuarioTransaccion usuarioTransaccion;
    
    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;

    @GetMapping
    public String Index(Model model) {
        Result result = usuarioDAOImplementation.GetAll();
        Result resultJPA = usuarioJPADAOImplementation.GetAll();
        model.addAttribute("usuarios", resultJPA.objects);
        model.addAttribute("errores", new ArrayList<>());
        model.addAttribute("isCorrect", false);

        return "UsuarioIndex";

    }
    
    @GetMapping("/cargamasiva/procesar")
    public String CargaMasivaProcesar(HttpSession session, Model model,
            RedirectAttributes redirectAttributes) {

        String Path = session.getAttribute("archivoCargaMasiva").toString();

        File archivo = new File(Path);

        String extension = archivo.getName().split("\\.")[1];
        List<Usuario> usuarios = new ArrayList<>();

        switch (extension) {
            case "txt": {
                usuarios = LecturaTxt(new File(Path));
                break;
            }
            case "xlsx": {
                System.out.print("Es un archivo excel");
                usuarios = LecturaXlsx(new File(Path));
                break;
            }
            default:
                System.out.print("Es otro tipo de archivo");
                break;
        }

        List<ErrorCarga> errores = new ArrayList<>();

        if (usuarios != null && !usuarios.isEmpty()) {

            errores = ValidarCampos(usuarios);
            if (errores == null || errores.isEmpty()) {

                usuarioDAOImplementation.AddAll(usuarios);
                System.out.print("Las datos no contienen errores");
            } else {

                System.out.print("Las datos contienen errores");
            }
            System.out.print("La lista  contiene datos");
        } else {

            System.out.println("La lista de usuarios no contiene datos");

        }

        Result result = usuarioDAOImplementation.GetAll();
        model.addAttribute("usuarios", result.objects);
        model.addAttribute("errores", new ArrayList<>());
        model.addAttribute("isCorrect", false);
        
        if(result.correct){
        
            redirectAttributes.addFlashAttribute("successAddAll", "Se agregaron correctamente");
        
        } else {
        
            redirectAttributes.addFlashAttribute("successAddAll", "No agregaron correctamente");
        
        
        }
        
        return "UsuarioIndex";
    }

    @PostMapping("/cargamasiva")
    public String CargaMasiva(@RequestParam("archivo") MultipartFile archivo,
            RedirectAttributes redirectAttributes, Model model, HttpSession session) {
        List<ErrorCarga> errores = new ArrayList<>();
        if (archivo != null && !archivo.isEmpty()) {

            String extension = archivo.getOriginalFilename().split("\\.")[1];

            String path = System.getProperty("user.dir");
            String pathArchivo = "src/main/resources/archivosCarga";
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
            String pathDefinitvo = path + "/" + pathArchivo + "/" + fecha + archivo.getOriginalFilename();

            try {
                archivo.transferTo(new File(pathDefinitvo));
                System.out.println("Se creo el archivo");
            } catch (IOException ex) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalStateException ex) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.print("extension:" + extension);

            List<Usuario> usuarios = new ArrayList<>();

            switch (extension) {
                case "txt": {
                    usuarios = LecturaTxt(new File(pathDefinitvo));
                    break;
                }
                case "xlsx": {
                    System.out.print("Es un archivo excel");
                    usuarios = LecturaXlsx(new File(pathDefinitvo));
                    break;
                }
                default:
                    System.out.print("Es otro tipo de archivo");
                    break;
            }

            if (usuarios != null && !usuarios.isEmpty()) {

                errores = ValidarCampos(usuarios);
                if (errores.isEmpty()) {
                    session.setAttribute("archivoCargaMasiva", pathDefinitvo);
//                    CargaMasivaProcesar(session);
                    model.addAttribute("listaUsuarios", usuarios);
                    model.addAttribute("errores", errores);
                    model.addAttribute("isCorrect", true);
                   
                    System.out.print("Las datos no contienen errores");
                } else {

                    model.addAttribute("errores", errores);
                    session.setAttribute("archivoCargaMasiva", pathDefinitvo);
                }
                System.out.print("La lista  contiene datos");
            } else {

                System.out.println("La lista de usuarios no contiene datos");

            }
        }

        Result result = usuarioDAOImplementation.GetAll();
        model.addAttribute("usuarios", result.objects);

        return "UsuarioIndex";

    }

    public List<Usuario> LecturaXlsx(File archivo) {
        List<Usuario> usuarios = new ArrayList<>();
        try (InputStream fileInputStream = new FileInputStream(archivo); XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {

            XSSFSheet workSheet = workbook.getSheetAt(0);

            for (Row row : workSheet) {

                Usuario usuario = new Usuario();
                usuario.setUserName(row.getCell(0).toString());
                usuario.setNombre(row.getCell(1).toString());
                usuario.setApellidoPaterno(row.getCell(2).toString());
                usuario.setApellidoMaterno(row.getCell(3).toString());
                usuario.setEmail(row.getCell(4).toString());
                usuario.setPassword(row.getCell(5).toString());

                String fecha = row.getCell(6).toString();
                SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
                usuario.setFechaNacimiento(format.parse(fecha));

                char sexo = row.getCell(7).toString().charAt(0);
                usuario.setSexo(sexo);

                usuario.setTelefono(row.getCell(8).toString());
                usuario.setCelular(row.getCell(9).toString());
                usuario.setCurp(row.getCell(10).toString());
                usuario.Roll = new Roll();

                String number = row.getCell(11).toString();
                int value = number.charAt(0);
                usuario.Roll.setIdRoll(value);

                usuarios.add(usuario);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }
        return usuarios;
    }

    public List<Usuario> LecturaTxt(File archivo) {
        List<Usuario> usuarios = new ArrayList<>();

        try (InputStream fileInputStream = new FileInputStream(archivo); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));) {
            String linea = "";

            while ((linea = bufferedReader.readLine()) != null) {

                String[] campos = linea.split("\\|");
                Usuario usuario = new Usuario();
                usuario.setUserName(campos[0]);
                usuario.setNombre(campos[1]);
                usuario.setApellidoPaterno(campos[2]);
                usuario.setApellidoMaterno(campos[3]);
                usuario.setEmail(campos[4]);
                usuario.setPassword(campos[5]);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                usuario.setFechaNacimiento(simpleDateFormat.parse(campos[6]));

                char sexo = campos[7].charAt(0);
                usuario.setSexo(sexo);

                usuario.setTelefono(campos[8]);
                usuario.setCelular(campos[9]);
                usuario.setCurp(campos[10]);

                usuario.Roll = new Roll();

                int number = Integer.parseInt(campos[11]);
                usuario.Roll.setIdRoll(number);

                System.out.println("usuario : " + usuario.getNombre());
                usuarios.add(usuario);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }

        return usuarios;

    }

    public List<ErrorCarga> ValidarCampos(List<Usuario> usuarios) {

        List<ErrorCarga> erroresCarga = new ArrayList<>();

        int lineaError = 0;

        for (Usuario usuario : usuarios) {

            lineaError++;
            BindingResult bindingResult = usuarioValidator.validateObject(usuario);

            List<ObjectError> errors = bindingResult.getAllErrors();

            for (ObjectError error : errors) {

                FieldError fieldError = (FieldError) error;
                ErrorCarga errorCarga = new ErrorCarga();
                errorCarga.campo = fieldError.getField();
                errorCarga.descripcion = fieldError.getDefaultMessage();
                errorCarga.linea = lineaError;
                erroresCarga.add(errorCarga);
//                System.out.println(errorCarga.campo + "" + errorCarga.descripcion);
            }

        }
        return erroresCarga;
    }

    @GetMapping("delete/{id}")
    public String Delete(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {

        Result result = usuarioJPADAOImplementation.Delete(id);
        redirectAttributes.addFlashAttribute("successDeleteMessage", result.object);
        return "redirect:/usuario";
    }
    
    //con JPA
    @GetMapping("detail/{id}")
    public String Form(@PathVariable("id") int id, Model model) {
        Result result = usuarioJPADAOImplementation.GetById(id);
        if (result.correct) {

            model.addAttribute("usuario", result.object);
            model.addAttribute("rolles", rollDAOImplementation.GetAll().objects);
            model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
            model.addAttribute("direccion", new Direccion());
            return "UsuarioDetail";
        } else {

            return "redirect:/usuario?error=Usuario no encontrado";

        }

    }

    @PostMapping("/detail")
    public String Update(@Validated(ValidationGroup.OnUpdate.class) @ModelAttribute("usuario") Usuario usuario,
            BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {
        
        

        if (bindingResult.hasErrors()) {
        
            model.addAttribute("error", "Error al actualizar usuario: " );
            model.addAttribute("rolles", rollDAOImplementation.GetAll().objects);
            redirectAttributes.addFlashAttribute("errorMessage", "El usuario " + usuario.getUserName() + "no se creo" + "Error:" + bindingResult.getAllErrors().toString());
            
        } else {
            
            Result result = usuarioJPADAOImplementation.Update(usuario);
            Usuario usuarioUpdate = (Usuario) result.object;
            
             if (result.correct) {
                redirectAttributes.addFlashAttribute("successMessage", "El usuario " + usuarioUpdate.getNombre() + "se actualizo con exito.");
                

            }
        }
        
        return "redirect:/usuario/detail/" + usuario.getIdUsuario();

    }

    @GetMapping("add")
    public String Add(Model model) {
        //System.out.print("Entro a la funcion");

        Usuario usuario = new Usuario();
        model.addAttribute("Usuario", usuario);

        model.addAttribute("rolles", rollDAOImplementation.GetAll().objects);

        model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
        model.addAttribute("direccion", new Direccion());
        return "UsuarioForm";
    }

    @PostMapping("add")
    public String Add(@Valid @ModelAttribute("Usuario") Usuario usuario,
            BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes,
            @RequestParam("imagenFile") MultipartFile imagenFile) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("Usuario", usuario);

            model.addAttribute("rolles", rollDAOImplementation.GetAll().objects);
            model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
            model.addAttribute("direccion", new Direccion());

            return "UsuarioForm";
        }

        if (imagenFile != null) {

            try {

                //vuelvo a asegurarme que es jpg o png
                String extension = imagenFile.getOriginalFilename().split("\\.")[1];

                if (extension.equals("jpg") || extension.equals("png")) {

                    byte[] byteImagen = imagenFile.getBytes();

                    String imagenBase64 = Base64.getEncoder().encodeToString(byteImagen);

                    usuario.setImagen(imagenBase64);

                }

            } catch (IOException ex) {

                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);

            }

        }

        Result result = usuarioJPADAOImplementation.Add(usuario);

        if (result.correct) {

            redirectAttributes.addFlashAttribute("successMessage", "El usuario " + usuario.getUserName() + "se creo con exito.");
        } else {

            redirectAttributes.addFlashAttribute("successMessage", "El usuario " + usuario.getUserName() + "no se creo");
        }
        return "redirect:/usuario";

    }

    @PostMapping("/updateImagen/{idUsuario}")
    public String UpdateImagen(@PathVariable("idUsuario") Integer idUsuario, 
        @RequestParam("imagen") MultipartFile imagenFile) {
        
        Result result  = usuarioDAOImplementation.GetById(idUsuario);
        Usuario usuario =  (Usuario) result.object;
        
        try {
            //Conversion de imagen;
            String imagen = Base64.getEncoder().encodeToString(imagenFile.getBytes());
            usuario.setImagen(imagen);
            usuarioDAOImplementation.UpdateImagen(usuario);
            
        } catch (IOException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "redirect:/usuario/detail/" + usuario.getIdUsuario();
    }

    @PostMapping("actiondireccion/{idUsuario}")
    public String ActionDireccion(@PathVariable("idUsuario") int idUsuario, @ModelAttribute("direccion") Direccion direccion,
            BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {

        model.addAttribute("rolles", rollDAOImplementation.GetAll().objects);
        model.addAttribute("paises", paisDAOImplementation.GetAll().objects);

        if (direccion.getIdDireccion() == 0) { // agregar direccion a usuario

            //usuarioDAOImplementation.AddDireccion(direccion, idUsuario);
            usuarioJPADAOImplementation.AddDireccion(direccion, idUsuario);
            redirectAttributes.addFlashAttribute("successAddDireccionMessage", "Dirección agregada correctamente");

        } else { // editar la direccion a usuario

            usuarioJPADAOImplementation.UpdateDireccion(direccion, idUsuario);
            redirectAttributes.addFlashAttribute("successUpdateDireccionMessage", "Dirección actualizada correctamente");
        }

        return "redirect:/usuario/detail/" + idUsuario;

    }

    @GetMapping("direccion/{idDireccion}")
    @ResponseBody
    public Result GetDireccionById(@PathVariable("idDireccion") int idDireccion,
            Model model) {

        return usuarioDAOImplementation.GetByIdDireccion(idDireccion);

    }

    @GetMapping("deleteDireccion/{idDireccion}")
    public String DeleteDireccion(@PathVariable("idDireccion") int idDireccion, Model model, RedirectAttributes redirectAttributes) {

        Result result = usuarioDAOImplementation.DeleteDireccion(idDireccion);
        redirectAttributes.addFlashAttribute("successDeleteDireccionMessage", result.object);
        return "redirect:/usuario";
    }

    @GetMapping("estado/{idPais}")
    @ResponseBody
    public Result GetEstadoByIdPais(@PathVariable("idPais") int idPais) {
        return estadoImplementation.GetByIdPais(idPais);
    }

    @GetMapping("municipio/{idEstado}")
    @ResponseBody
    public Result GetMunicipioByEstado(@PathVariable("idEstado") int idEstado) {

        return municipioImplementation.GetByIdEstado(idEstado);
    }

    @GetMapping("colonia/{idMunicipio}")
    @ResponseBody
    public Result GetColoniaByMunicipio(@PathVariable("idMunicipio") int idMunicipio) {

        return coloniaImplementation.GetByIdMunicipio(idMunicipio);
    }

    @GetMapping("codigoPostal/{idColonia}")
    @ResponseBody
    public Result CodigoPostalByColonia(@PathVariable("idColonia") int idColonia) {

        return coloniaImplementation.CodigoPostalByColonia(idColonia);
    }

}
