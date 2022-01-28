package com.tenniscourts.tenniscourts;

import com.tenniscourts.schedules.ScheduleService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

@FixMethodOrder
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration
public class TennisCourtsServiceTest {
    @Mock
    TennisCourtRepository tennisCourtRepository;
    @Mock
    TennisCourtMapper tennisCourtMapper;
    @Mock
    ScheduleService scheduleService;
    @InjectMocks
    TennisCourtService tennisCourtService;
    @Rule
   public ExpectedException expected = ExpectedException.none();

    @Test
    public void shouldSaveAnTennisCourtWhenPassName() {

        Mockito.when(tennisCourtMapper.map(Mockito.any(TennisCourtDTO.class))).thenReturn(new TennisCourt());
        Mockito.when(tennisCourtRepository.saveAndFlush(Mockito.any())).thenReturn(new TennisCourt());
        TennisCourtDTO entityCreatedDTO = new TennisCourtDTO();
        entityCreatedDTO.setId(1L);
        Mockito.when(tennisCourtMapper.map(Mockito.any(TennisCourt.class))).thenReturn(entityCreatedDTO);

        TennisCourtDTO tennisCourt = new TennisCourtDTO();
        tennisCourt.setName("teste");
        tennisCourt = tennisCourtService.addTennisCourt(tennisCourt);

        Mockito.verify(tennisCourtRepository).saveAndFlush(new TennisCourt());
        Assert.assertNotNull(tennisCourt.getId());
    }
    @Test
    public void shouldReturnTennisCourtById() {
        Mockito.when(tennisCourtMapper.map(Mockito.any(TennisCourtDTO.class))).thenReturn(new TennisCourt());
        TennisCourt tennisCourt = new TennisCourt();
        tennisCourt.setId(1L);
        tennisCourt.setName("TESTE");
        TennisCourtDTO tennisCourtDTO =  new TennisCourtDTO();
        tennisCourtDTO.setId(tennisCourt.getId());
        Mockito.when(tennisCourtRepository.findById(1L)).thenReturn(Optional.of(tennisCourt));
        Mockito.when(tennisCourtMapper.map(Mockito.any(TennisCourt.class))).thenReturn(tennisCourtDTO);
         tennisCourtService.findTennisCourtById(1L);

        Assert.assertNotNull(tennisCourtDTO.getId());
        Mockito.verify(tennisCourtRepository).findById(1L);
        Assert.assertEquals(tennisCourtDTO, tennisCourtService.findTennisCourtById(1L)) ;




    }


}
