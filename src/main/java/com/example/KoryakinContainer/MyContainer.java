package com.example.KoryakinContainer;

/**
 * Простой контейнер, реализованный поверх массива.
 * Поддерживает добавление элементов в конец, получение по индексу и удаление с сдвигом.
 *
 * @param <T> тип элементов, которые может хранить контейнер
 */
public class MyContainer<T> {

    /** Внутренний массив для хранения элементов. Не использовать напрямую вне класса. */
    private Object[] storage;

    /** Текущее количество элементов в контейнере (индекс следующей свободной ячейки). */
    private int count;

    /** Начальная ёмкость внутреннего массива. */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Конструктор по умолчанию: создаёт внутренний массив с начальной ёмкостью.
     * Поле count инициализируется нулём — контейнер пуст.
     */
    public MyContainer() {
        storage = new Object[DEFAULT_CAPACITY];
        count = 0;
    }

    /**
     * Увеличивает ёмкость внутреннего массива.
     * Создаёт новый массив в два раза большего размера и копирует
     * в него все текущие элементы с помощью System.arraycopy.
     */
    private void grow() {
        Object[] newArr = new Object[storage.length * 2];
        System.arraycopy(storage, 0, newArr, 0, count);
        storage = newArr;
    }

    /**
     * Добавляет элемент в конец контейнера.
     * Если внутренний массив заполнен — автоматически расширяет его.
     * Разрешены значения null.
     *
     * @param item элемент для добавления
     */
    public void add(T item) {
        if (count == storage.length) {
            grow();
        }
        storage[count++] = item;
    }

    /**
     * Удаляет элемент по указанному индексу.
     * После удаления все элементы справа от индекса сдвигаются на одну позицию влево.
     * Если индекс некорректен — выбрасывается IndexOutOfBoundsException.
     *
     * @param index индекс удаляемого элемента (0..size()-1)
     */
    public void remove(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Некорректный индекс: " + index);
        }
        for (int i = index; i < count - 1; i++) {
            storage[i] = storage[i + 1];
        }
        storage[--count] = null; // освобождаем ссылку для GC
    }

    /**
     * Возвращает элемент по индексу с приведением к типу T.
     * Проводится проверка границ, в случае ошибки — IndexOutOfBoundsException.
     *
     * @param index индекс запрашиваемого элемента
     * @return элемент типа T
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Некорректный индекс: " + index);
        }
        return (T) storage[index];
    }

    /**
     * Возвращает количество элементов, хранящихся в контейнере.
     *
     * @return текущий размер (количество элементов)
     */
    public int size() {
        return count;
    }
}
