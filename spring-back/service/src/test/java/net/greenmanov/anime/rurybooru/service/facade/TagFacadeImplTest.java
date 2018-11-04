package net.greenmanov.anime.rurybooru.service.facade;

import net.greenmanov.anime.rurybooru.api.dto.TagDTO;
import net.greenmanov.anime.rurybooru.api.dto.TagInfoDTO;
import net.greenmanov.anime.rurybooru.api.facade.TagFacade;
import net.greenmanov.anime.rurybooru.persistance.entity.Tag;
import net.greenmanov.anime.rurybooru.service.BeanMappingService;
import net.greenmanov.anime.rurybooru.service.ServiceTestConfiguration;
import net.greenmanov.anime.rurybooru.service.TagService;
import net.greenmanov.iqdb.parsers.TagType;
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
 * Class TagFacadeImplTest
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@ContextConfiguration(classes = ServiceTestConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class TagFacadeImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private TagService tagService;

    @InjectMocks
    private TagFacade facade = new TagFacadeImpl();

    @Spy
    @Inject
    private BeanMappingService mappingService;

    private Tag tag;

    private Long tagCount = 123L;

    @BeforeMethod
    public void setUp() {
        tag = new Tag();
        tag.setName("Tag name");
        tag.setType(TagType.FAULTS);
        tag.setId(12L);

        MockitoAnnotations.initMocks(this);
        when(tagService.getById(tag.getId())).thenReturn(tag);
        when(tagService.getTagUseCount(tag)).thenReturn(tagCount);
    }

    @Test
    public void testGetById() {
        TagDTO tagDTO = facade.getById(tag.getId());
        verify(tagService).getById(tag.getId());
        Tag foundTag = mappingService.mapTo(tagDTO, Tag.class);
        assertEquals(tag, foundTag);
    }

    @Test
    public void testGetTagInfo() {
        TagInfoDTO tagInfo = facade.getTagInfo(tag.getId());
        verify(tagService).getById(tag.getId());
        verify(tagService).getTagUseCount(tag);
        assertEquals(tagCount, tagInfo.getCount());
        Tag foundTag = mappingService.mapTo(tagInfo.getTag(), Tag.class);
        assertEquals(tag, foundTag);
    }
}