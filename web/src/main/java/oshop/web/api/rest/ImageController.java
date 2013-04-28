package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import oshop.dao.GenericDao;
import oshop.model.Image;
import oshop.web.api.rest.adapter.ReturningRestCallbackAdapter;
import oshop.web.api.rest.adapter.VoidRestCallbackAdapter;
import oshop.web.dto.FileUploadDto;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/images")
@Transactional(readOnly = true)
public class ImageController {

    private static final Log log = LogFactory.getLog(ImageController.class);

    @Resource
    private GenericDao<Image, Integer> imageDao;

    @RequestMapping(
            value = "/raw",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<?> add(final HttpEntity<byte[]> requestEntity) {
        return new ReturningRestCallbackAdapter<Integer>() {
            @Override
            protected Integer getResult() throws Exception {
                if (!requestEntity.hasBody()) {
                    throw new Exception("No image to save");
                }

                Image image = new Image();
                image.setContentType(requestEntity.getHeaders().getContentType().toString());
                image.setData(requestEntity.getBody());

                return imageDao.add(image);
            }
        }.invoke();
    }

    @RequestMapping(
            value = "/",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<?> addViaFormSubmit(final FileUploadDto uploadDto) {
        return new ReturningRestCallbackAdapter<List<Integer>>() {
            @Override
            protected List<Integer> getResult() throws Exception {
                List<Integer> ids = new ArrayList<Integer>(uploadDto.getFiles().size());

                for (MultipartFile file: uploadDto.getFiles()) {
                    if (file.getBytes() == null || file.getBytes().length == 0) {
                        throw new Exception("No image to save");
                    }

                    Image image = new Image();
                    image.setContentType(file.getContentType());
                    image.setData(file.getBytes());

                    ids.add(imageDao.add(image));
                }

                return ids;
            }
        }.invoke();
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE)
    @Transactional(readOnly = false)
    public ResponseEntity<?> delete(@PathVariable final Integer id) {
        return new VoidRestCallbackAdapter() {
            @Override
            protected void perform() throws Exception {
                imageDao.remove(id);            }
        }.invoke();
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable final Integer id) {
        return new ReturningRestCallbackAdapter<byte[]>() {
            @Override
            protected byte[] getResult() throws Exception {
                Image image = imageDao.get(id);

                this.getHeaders().setContentType(MediaType.parseMediaType(image.getContentType()));
                this.getHeaders().setContentLength(image.getData().length);

                return image.getData();
            }
        }.invoke();
    }

    @RequestMapping(
            value = "/raw/{id}",
            method = RequestMethod.PUT)
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<?> update(
            @PathVariable final Integer id,
            final HttpEntity<byte[]> requestEntity) {

        return new VoidRestCallbackAdapter() {
            @Override
            protected void perform() throws Exception {
                if (!requestEntity.hasBody()) {
                    throw new Exception("No image to save");
                }

                Image image = imageDao.get(id);
                image.setContentType(requestEntity.getHeaders().getContentType().toString());
                image.setData(requestEntity.getBody());
                imageDao.update(image);
            }
        }.invoke();
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT)
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<?> updateViaFormSubmit(
            @PathVariable final Integer id,
            final @RequestParam MultipartFile file) {

        return new VoidRestCallbackAdapter() {
            @Override
            protected void perform() throws Exception {
                Image image = imageDao.get(id);
                image.setContentType(file.getContentType());
                image.setData(file.getBytes());

                imageDao.update(image);
            }
        }.invoke();
    }


    @RequestMapping(
            value = "/update",
            method = {RequestMethod.POST})
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<?> updateMultipleViaFormSubmit(
            final @RequestParam List<String> id,
            final FileUploadDto uploadDto) {

        return new VoidRestCallbackAdapter() {
            @Override
            protected void perform() throws Exception {

                for (int i = 0; i < uploadDto.getFiles().size(); i++) {
                    MultipartFile file = uploadDto.getFiles().get(i);

                    Image image = imageDao.get(Integer.valueOf(id.get(i)));
                    image.setContentType(file.getContentType());
                    image.setData(file.getBytes());

                    imageDao.update(image);
                }
            }
        }.invoke();
    }
}
