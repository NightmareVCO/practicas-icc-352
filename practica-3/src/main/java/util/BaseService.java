package util;

public abstract class BaseService<T> {
  protected abstract T findById(Long id);
  abstract public T insert(T entity);
  abstract public T update(T entity);
  abstract public T delete(T entity);
  abstract public T deleteById(Long id);
  abstract public T deleteAll();
}
