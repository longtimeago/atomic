package ua.dp.skillsup;

/**
 * @author Andrey Lomakin <a href="mailto:lomakin.andrey@gmail.com">Andrey Lomakin</a>
 * @since 03/11/14
 */
public class CounterFactory {
	public enum CounterType {
		ATOMIC, FAST //, DIVIDE_ONE, DIVIDE_TWO
	}

	public static Counter build(CounterType type) {
		switch (type) {
			case ATOMIC:
				return new AtomicCounter();
            case FAST:
                return new FastCounter(256);
//			case DIVIDE_ONE:
//				return new CounterDivideAndRuleOne();
//			case DIVIDE_TWO:
//				return new CounterDivideAndRuleOne();
		}

		throw new IllegalArgumentException();
	}
}