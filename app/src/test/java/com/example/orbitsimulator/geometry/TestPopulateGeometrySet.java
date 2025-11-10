package com.example.orbitsimulator.geometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.function.Supplier;

@RunWith(MockitoJUnitRunner.class)
public class TestPopulateGeometrySet {

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
    public void populateGeometrySet_ShouldClearBeforeAdding(){
        //Cria o elemento na lista limpa pelo setUp()
        ElementTypes oldElement = mock(ElementTypes.class);
        geometry.getGeometrySet().add(oldElement);

        //Verifica se o elemento foi criado pesquisando o tamanho da lista
        assertEquals("Condição inicial, deve haver um elemento na lista", 1, geometry.getGeometrySet().size());

        //inicializa o método a ser testado e verifica se o elemento criado pelo mock NÃO ESTÁ PRESENTE
        geometry.populateGeometrySet(mockFactory);
        assertFalse("A lista não deve conter o elemento antigo oldElement", geometry.getGeometrySet().contains(oldElement));

        int expectedSize = NUMBER_OF_ELEMENTS * NUMBER_OF_STRUCTURES;
        assertEquals("A lista deve conter o novo número de elementos", expectedSize, geometry.getGeometrySet().size());
    }

    @Test
    public void populateGeometrySet_ShouldHaveCorrectSize(){
        geometry.populateGeometrySet(mockFactory);

        int expectedSize = NUMBER_OF_ELEMENTS * NUMBER_OF_STRUCTURES;

    }
}
