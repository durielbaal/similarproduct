package capitole.exam.similarproduct.application.bus;

public interface UseCaseBus {
  <I, O> O dispatch(I input);
}
