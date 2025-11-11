package com.example.orbitsimulator.geometry;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.orbitsimulator.util.ColorRGB;
import com.example.orbitsimulator.util.PolarCoord;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.function.Supplier;

public class TestGeometrySetIncrement {

    private static final int NUMBER_OF_STRUCTURES = 10;
    private static final int NUMBER_OF_ELEMENTS = 20;
    private static final double MAX_RADIUS = 400;

    @Mock
    private Supplier<ElementTypes> mockFactory;

    @Mock
    private ElementTypes mockElement;

    private Geometry geometry;

    @Before
    public void setUp(){
        geometry = Geometry.getInstance();
        geometry.getGeometrySet().clear();

        when(mockFactory.get()).thenReturn(mockElement);
    }
    @Test
    public void getGeometrySetIncrement_ShouldReturnIncrementedList() {
        // --- Arrange ---

        // 1. Crie mocks para os elementos "antigos" e seus dados
        PolarCoord mockOldPos1 = mock(PolarCoord.class);
        ColorRGB mockColor1 = mock(ColorRGB.class);
        ElementTypes mockOldElement1 = mock(ElementTypes.class);

        // Defina o comportamento dos elementos antigos
        when(mockOldElement1.getPosition()).thenReturn(mockOldPos1);
        when(mockOldPos1.getAngle()).thenReturn(1.5); // radianos
        when(mockOldPos1.getRadius()).thenReturn(100.0);
        when(mockOldElement1.getColor()).thenReturn(mockColor1);

        // 2. Crie mocks para os elementos "novos" que a fábrica irá criar
        ElementTypes mockNewElement1 = mock(ElementTypes.class);

        // 3. Configure a fábrica para retornar o novo elemento
        // (Assumindo que a factory foi setada no setUp ou em outro teste)
        // Se a factory não estiver setada, precisamos setá-la:
        geometry.setElementFactory(mockFactory); // Assumindo acesso package-private
        when(mockFactory.get()).thenReturn(mockNewElement1);

        // 4. Adicione o elemento antigo à lista interna do Singleton
        geometry.getGeometrySet().clear();
        geometry.getGeometrySet().add(mockOldElement1);

        // 5. Defina o valor do deslocamento
        geometry.setDisplacementSum(20.0); // Assumindo acesso package-private

        // --- Act ---
        ArrayList<ElementTypes> resultList = geometry.getGeometrySet();

        // --- Assert ---

        // 1. Verifique o tamanho da lista e o conteúdo
        assertEquals("A lista de resultado deve ter 1 elemento", 1, resultList.size());
        assertEquals("O elemento na lista deve ser o 'novo' elemento",
                mockNewElement1, resultList.get(0));

        // 2. Verifique se a fábrica foi chamada
        verify(mockFactory, times(1)).get();

        // 3. Verifique se a cor foi copiada para o novo elemento
        verify(mockNewElement1).setColor(mockColor1);

        // 4. Calcule o ângulo esperado
        // newAngle = oldAngle + (displacementSum / oldRadius)
        double expectedAngle = 1.5 + (20.0 / 100.0); // 1.5 + 0.2 = 1.7

        // 5. Capture o PolarCoord que foi passado para o setPosition do NOVO elemento
        ArgumentCaptor<PolarCoord> captor = ArgumentCaptor.forClass(PolarCoord.class);
        verify(mockNewElement1).setPosition(captor.capture());

        PolarCoord capturedCoord = captor.getValue();
        assertEquals("O raio deve ser o mesmo do elemento antigo",
                100.0, capturedCoord.getRadius(), 0.001);
        assertEquals("O ângulo deve ser calculado corretamente",
                expectedAngle, capturedCoord.getAngle(), 0.001);
    }


}
