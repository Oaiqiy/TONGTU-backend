package com.tongtu.tongtu.data;

import com.tongtu.tongtu.domain.DeletedFile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DeletedFileRepository extends CrudRepository<DeletedFile,Long> {

}
