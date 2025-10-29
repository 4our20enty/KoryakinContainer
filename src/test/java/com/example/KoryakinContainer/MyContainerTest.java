package com.example.KoryakinContainer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit-тесты для класса MyContainer.
 */
public class MyContainerTest {

    private MyContainer<String> stringContainer;
    private MyContainer<Integer> intContainer;

    @BeforeEach
    public void setUp() {
        stringContainer = new MyContainer<>();
        intContainer = new MyContainer<>();
    }

    @Test
    public void testAddAndGet() {
        stringContainer.add("A");
        stringContainer.add("B");

        assertEquals(2, stringContainer.size(), "Размер после двух добавлений должен быть 2");
        assertEquals("A", stringContainer.get(0));
        assertEquals("B", stringContainer.get(1));
    }

    @Test
    public void testRemoveByIndexShiftsElements() {
        stringContainer.add("one");
        stringContainer.add("two");
        stringContainer.add("three");

        stringContainer.remove(1); // удаляем "two"

        assertEquals(2, stringContainer.size());
        assertEquals("one", stringContainer.get(0));
        assertEquals("three", stringContainer.get(1), "Элемент справа от удалённого должен сдвинуться влево");
    }

    @Test
    public void testRemoveFirstElementWhenNullPresent() {
        stringContainer.add(null);
        stringContainer.add("val");

        assertEquals(2, stringContainer.size());
        assertNull(stringContainer.get(0), "Первый элемент должен быть null");
        stringContainer.remove(0); // удаляем null
        assertEquals(1, stringContainer.size());
        assertEquals("val", stringContainer.get(0));
    }

    @Test
    public void testIndexOutOfBoundsOnGet() {
        // пустой контейнер — любая попытка получить по индексу должна бросать
        assertThrows(IndexOutOfBoundsException.class, () -> stringContainer.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> stringContainer.get(-1));
    }

    @Test
    public void testIndexOutOfBoundsOnRemove() {
        stringContainer.add("only");
        assertThrows(IndexOutOfBoundsException.class, () -> stringContainer.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> stringContainer.remove(1));
    }

    @Test
    public void testGrowBeyondInitialCapacity() {
        // начальная ёмкость 10 — добавим больше, чтобы проверить корректное расширение
        int items = 25;
        for (int i = 0; i < items; i++) {
            intContainer.add(i);
        }

        assertEquals(items, intContainer.size());
        assertEquals(Integer.valueOf(0), intContainer.get(0));
        assertEquals(Integer.valueOf(items - 1), intContainer.get(items - 1));
    }

    @Test
    public void testMultipleNullsAndRemovals() {
        stringContainer.add(null);
        stringContainer.add(null);
        stringContainer.add("x");

        assertEquals(3, stringContainer.size());
        assertNull(stringContainer.get(0));
        assertNull(stringContainer.get(1));
        assertEquals("x", stringContainer.get(2));

        // удалить средний null
        stringContainer.remove(1);
        assertEquals(2, stringContainer.size());
        assertNull(stringContainer.get(0));
        assertEquals("x", stringContainer.get(1));
    }
}
