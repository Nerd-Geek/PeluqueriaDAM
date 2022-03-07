package ies.luisvives.serverpeluqueriadam.controllers.files;

import ies.luisvives.serverpeluqueriadam.controller.FilesRestController;
import ies.luisvives.serverpeluqueriadam.exceptions.storage.StorageException;
import ies.luisvives.serverpeluqueriadam.model.User;
import ies.luisvives.serverpeluqueriadam.model.UserGender;
import ies.luisvives.serverpeluqueriadam.repository.UserRepository;
import ies.luisvives.serverpeluqueriadam.services.uploads.FileSystemStorageService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FilesControllerMockTest {
    @MockBean
    private final FileSystemStorageService fileSystemStorageService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user.setAppointments(null);
        userRepository.save(user);
    }

    private final User user = User.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .username("nombre usuario")
            .name("nombre")
            .surname("apellido")
            .phoneNumber("912345678")
            .gender(UserGender.Male)
            .email("adsada@sdasdd.com")
            .password("asd")
            .build();

    @InjectMocks
    private FilesRestController filesRestController;

    @Autowired
    public FilesControllerMockTest(FileSystemStorageService fileSystemStorageService) {
        this.fileSystemStorageService = fileSystemStorageService;
    }

    //    @Test
//    @Order(1)
//    void getAllTestMock() {
//
//        Mockito.when(loginRepository.findAll())
//                .thenReturn(List.of(login));
//
//        Mockito.when(loginMapper.toDTO(List.of(login))).thenReturn(List.of(dto));
//
//        var response = loginController.findAll();
//        var res = response.getBody();
//
//        assertAll(
//                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
//                () -> assertEquals(res.get(0).getUser().getUsername(), login.getUser().getUsername()),
//                () -> assertEquals(res.get(0).getInstance(), login.getInstant()),
//                () -> assertEquals(res.get(0).getToken(), login.getToken())
//        );
//    }
//
//    @Test
//    @Order(2)
//    void serveFileTestMock() {
    // TODO:  mockHttpServletRequest ??
//        LoginUserDTO loginUserDTO = LoginUserDTO.builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .build();
//        LoginDTO dto = LoginDTO.builder()
//                .user(loginUserDTO)
//                .instance(login.getInstant())
//                .token(login.getToken())
//                .build();
//
//        Mockito.when(loginRepository.findById(login.getId()))
//                .thenReturn(Optional.of(login));
//
//        Mockito.when(loginMapper.toDTO(login)).thenReturn(dto);
//
//        var response = loginController.findById(login.getId());
//        var res = response.getBody();
//
//        assertAll(
//                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
//                () -> assertEquals(res.getUser().getUsername(), login.getUser().getUsername()),
//                () -> assertEquals(res.getInstance(), login.getInstant()),
//                () -> assertEquals(res.getToken(), login.getToken())
//        );
//
//        Mockito.verify(loginRepository, Mockito.times(1)).findById(login.getId());
//        Mockito.verify(loginMapper, Mockito.times(1)).toDTO(login);
//    }
//
    @Test
    @Order(3)
    void uploadFileTestMock() {
        MultipartFile file = new MockMultipartFile("C:\\Users\\Madirex\\Pictures\\2020.png", "image1",
                "application/doc", "image".getBytes());
        //System.out.println(file.get);

        //TODO: nullpointer
        System.out.println(filesRestController.uploadFile(file).getStatusCode());
    }

    @Test
    @Order(4)
    void uploadFileException() {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        Exception ex = assertThrows(StorageException.class, () -> {
            filesRestController.uploadFile(file);
        });

        assertNotNull(file);
        System.out.println(ex.getMessage());
        assertTrue(ex.getMessage().contains("file"));
    }

    @Test
    @Order(5)
    void uploadFileNullPointerException() {
        Exception ex = assertThrows(NullPointerException.class, () -> {
            filesRestController.uploadFile(null);
        });
        assertTrue(ex.getMessage().contains("file"));
    }
}