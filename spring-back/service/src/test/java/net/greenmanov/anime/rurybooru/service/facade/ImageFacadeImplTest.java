package net.greenmanov.anime.rurybooru.service.facade;

import net.greenmanov.anime.rurybooru.service.ServiceTestConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class ImageFacadeImplTest
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@ContextConfiguration(classes = ServiceTestConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ImageFacadeImplTest {

}