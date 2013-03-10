package oshop.dao;

import org.springframework.stereotype.Repository;
import oshop.model.Album;

@Repository
public class AlbumDao /*extends GenericDao<Album, Integer>*/ {

    //@Override
    protected Class<Album> getDomainClass() {
        return Album.class;
    }
}
