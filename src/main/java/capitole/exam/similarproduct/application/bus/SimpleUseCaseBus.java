package capitole.exam.similarproduct.application.bus;

import capitole.exam.similarproduct.application.useCase.UseCase;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

@Component
public class SimpleUseCaseBus implements UseCaseBus {

  private final Map<Class<?>, UseCase<?, ?>> useCaseMap = new HashMap<>();

  public SimpleUseCaseBus(List<UseCase<?, ?>> useCases) {
    for (UseCase<?, ?> useCase : useCases) {
      Class<?> inputType = Objects.requireNonNull(GenericTypeResolver.resolveTypeArguments(useCase.getClass(), UseCase.class))[0];
      useCaseMap.put(inputType, useCase);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public <I, O> O dispatch(I input) {
    UseCase<I, O> useCase = (UseCase<I, O>) useCaseMap.get(input.getClass());
    if (useCase == null) {
      throw new RuntimeException("No UseCase found for: " + input.getClass().getSimpleName());
    }
    return useCase.execute(input);
  }
}
