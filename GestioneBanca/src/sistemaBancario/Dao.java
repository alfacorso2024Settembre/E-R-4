package sistemaBancario;

import java.sql.SQLException;
import java.util.List;

public interface Dao <T,I>{


//    T getById(I indice) throws sistemaBancario.DaoException, SQLException;
//    List<T> find(Object searchText) throws sistemaBancario.DaoException, SQLException;
//



    int add(T elemento) throws DaoException, SQLException;
    List<T> read(int limit,int offset) throws DaoException, SQLException;
    int update(I id, T elemento) throws DaoException, SQLException;
    int delete(I id) throws DaoException, SQLException;
    List<T> find(T searchText) throws DaoException;

}
