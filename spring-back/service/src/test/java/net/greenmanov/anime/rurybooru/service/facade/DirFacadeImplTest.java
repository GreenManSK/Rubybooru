package net.greenmanov.anime.rurybooru.service.facade;

import net.greenmanov.anime.rurybooru.api.dto.DirDTO;
import net.greenmanov.anime.rurybooru.api.facade.DirFacade;
import net.greenmanov.anime.rurybooru.persistance.entity.Dir;
import net.greenmanov.anime.rurybooru.persistance.entity.Image;
import net.greenmanov.anime.rurybooru.service.BeanMappingService;
import net.greenmanov.anime.rurybooru.service.DirService;
import net.greenmanov.anime.rurybooru.service.ServiceTestConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Class DirFacadeImplTest
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@ContextConfiguration(classes = ServiceTestConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class DirFacadeImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private DirService dirService;

    @InjectMocks
    private DirFacade facade = new DirFacadeImpl();

    @Spy
    @Inject
    private BeanMappingService mappingService;

    private Dir dir1;
    private Dir dir2;

    private Image image1;
    private Image image2;
    private Image image3;

    @BeforeMethod
    public void setUp() {
        dir1 = new Dir();
        dir1.setId(1L);

        dir2 = new Dir();
        dir2.setId(2L);
        dir2.setName("SubDir");
        dir2.setParent(dir1);

        image1 = new Image();
        image1.setName("Img1");
        image2 = new Image();
        image2.setName("Image2");
        image3 = new Image();
        image3.setName("Img3");

        dir1.addImage(image1);
        dir1.addImage(image2);
        dir2.addImage(image3);

        MockitoAnnotations.initMocks(this);
        when(dirService.getById(dir1.getId())).thenReturn(dir1);
    }

    @Test
    public void testGetById() {
        DirDTO dirDTO = facade.getById(dir1.getId());
        verify(dirService).getById(dir1.getId());
        Dir foundDir = mappingService.mapTo(dirDTO, Dir.class);
        assertEquals(dir1, foundDir);
    }
}