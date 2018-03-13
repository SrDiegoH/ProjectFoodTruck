package dao.jinq;

import java.io.Serializable;
import org.jinq.orm.stream.JinqStream.Where;

@FunctionalInterface
public interface IWhere<T> extends Where<T, Exception>, Serializable {
	
}
