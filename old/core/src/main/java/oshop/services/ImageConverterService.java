package oshop.services;

import java.io.IOException;

public interface ImageConverterService {

    public byte[] deflate(byte[] imageData, int newWidth, String fileType) throws IOException;
}
