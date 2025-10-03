package capitole.exam.similarproduct.application.useCase;

public interface UseCase<I, O> {
  O execute(I input);
}
