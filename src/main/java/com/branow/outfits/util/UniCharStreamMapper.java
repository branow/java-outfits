package com.branow.outfits.util;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * The {@code UniCharStreamMapper} lets to read bytes and convert them
 * to {@link UniChar} format gradually. This class is an expended version
 * of the class {@link UniCharMapper}. When {@link UniCharMapper} reads bytes
 * from {@link ByteBuffer} and convert them to {@link UniChar}, this class
 * allows to read big amount of byte data (especially files) in parts. Such approach
 * let not to keep big amount of data in the operative memory in one moment.
 * It is realised with abstract methods {@link UniCharStreamMapper#read(long, int)}
 * that returns the given number of bytes starting from the given position in
 * {@link ByteBuffer} representation.
 * <br>
 * It works so: it reads byte buffer with specific size (the initial size equals
 * 16 kb, but it can also be changed in constructor), creates an instance of {@link UniCharMapper}
 * to converts bytes to {@link UniChar} objects (it goes on in methods
 * {@link UniCharStreamMapper#hasNext()} and {@link UniCharStreamMapper#next()}), after read all bytes
 * of current byte buffer it starts reading new byte buffer (calling
 * {@link UniCharStreamMapper#read(long, int)}) if there are any more bytes.
 */
public abstract class UniCharStreamMapper {

    /**
     * The initial byte buffer size equaling number of bytes that is
     * in operative memory in one moment.
     */
    private static final int BUFFER_SIZE = 1024 * 16;

    private final long size;
    private final Charset charset;
    private final int bufferSize;


    private long bytePosition = 0;
    private long uniCharPosition = 0;
    private UniCharMapper mapper;
    private long previousBufferPos = 0;


    /**
     * @param size The number of bytes to read. For example, it has to be equal file size
     *             if this class works with files.
     * @see UniCharStreamMapper#UniCharStreamMapper(long, Charset, int)
     */
    public UniCharStreamMapper(long size) {
        this(size, Charset.defaultCharset(), BUFFER_SIZE);
    }

    /**
     * @param size    The number of bytes to read. For example, it has to be equal file size
     *                if this class works with files.
     * @param charset The charset to convert bytes to {@link UniChar} objects.
     * @see UniCharStreamMapper#UniCharStreamMapper(long, Charset, int)
     */
    public UniCharStreamMapper(long size, Charset charset) {
        this(size, charset, BUFFER_SIZE);
    }

    /**
     * @param size       The number of bytes to read. For example, it has to be equal file size
     *                   if this class works with files.
     * @param charset    The charset to convert bytes to {@link UniChar} objects.
     * @param bufferSize The buffer size to keep in memory in one moment.
     */
    public UniCharStreamMapper(long size, Charset charset, int bufferSize) {
        this.size = size;
        this.charset = charset;
        this.bufferSize = bufferSize;
    }

    /**
     * Tells weather there are ony elements {@link UniChar}. It returns {@code true}
     * if the current {@code bytePosition} is less than {@code size}.
     *
     * @return {@code True} if, and only if, there is at least one element.
     */
    public boolean hasNext() {
        return bytePosition < size;
    }

    /**
     * Returns next {@link UniChar} element.
     *
     * @return The next {@link UniChar} element.
     * @throws BufferUnderflowException if the byte buffer does not have remaining elements.
     */
    public UniChar next() {
        if (!hasNext())
            throw new BufferUnderflowException();

        validateMapper();
        UniChar uc = mapper.next();
        bytePosition += mapper.getBuffer().position() - previousBufferPos;
        previousBufferPos = mapper.getBuffer().position();
        uniCharPosition++;
        return uc;
    }

    /**
     * Returns the current byte position that equals to a number of read bytes.
     *
     * @return The current byte position.
     */
    public long getBytePosition() {
        return bytePosition;
    }

    /**
     * Returns the current uni char position that equals to a number of read
     * {@link UniChar} objects.
     *
     * @return The current byte position.
     */
    public long getUniCharPosition() {
        return uniCharPosition;
    }

    /**
     * Returns the {@code charset} of this mapper.
     *
     * @return The {@code charset} of this mapper.
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * Returns the {@code size} of this mapper.
     *
     * @return The {@code size} of this mapper.
     */
    public long getSize() {
        return size;
    }

    /**
     * Reads the given number of bytes starting from the given position.
     *
     * @param pos  The byte starting byte position.
     * @param size The number of bytes to read.
     * @return The byte buffer of the read bytes.
     */
    protected abstract ByteBuffer read(long pos, int size);


    private void validateMapper() {
        if (mapper == null || (mapper.getBuffer().position() + 4 > mapper.getBuffer().limit() && bytePosition + 4 <= size)) {
            previousBufferPos = 0;
            int newBufferSize = (int) Math.min(bufferSize, size - bytePosition);
            ByteBuffer buffer = read(bytePosition, newBufferSize);
            Charset charset = mapper != null ? mapper.getCharset() : this.charset;
            mapper = new UniCharMapper(buffer, charset);
        }
    }
}
