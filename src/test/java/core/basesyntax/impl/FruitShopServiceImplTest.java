package core.basesyntax.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operationstrategy.OperationStrategy;
import core.basesyntax.service.FruitShopService;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static OperationStrategy mockOperationStrategy;
    private static FruitShopService fruitShopService;
    private static List<FruitTransaction> fruitTransactions;
    private static FruitTransaction first_transaction;
    private static FruitTransaction second_transaction;

    @BeforeClass
    public static void beforeClass() {
        first_transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10);
        second_transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10);
        fruitTransactions = List.of(first_transaction, second_transaction);
    }

    @Before
    public void setUp() {
        mockOperationStrategy = mock(OperationStrategy.class);
        fruitShopService = new FruitShopServiceImpl(mockOperationStrategy);
    }

    @Test
    public void processTransactions_EmptyList_Ok() {
        fruitShopService.processTransactions(Collections.emptyList());
        verifyNoInteractions(mockOperationStrategy);
    }

    @Test
    public void processTransactions_SingleTransaction_Ok() {
        fruitShopService.processTransactions(Collections.singletonList(first_transaction));
        verify(mockOperationStrategy).handleOperation(first_transaction);
        verifyNoMoreInteractions(mockOperationStrategy);
    }

    @Test
    public void processTransactions_ListOfTransactions_Ok() {
        fruitShopService.processTransactions(List.of(first_transaction, second_transaction));
        verify(mockOperationStrategy).handleOperation(first_transaction);
        verify(mockOperationStrategy).handleOperation(second_transaction);
        verifyNoMoreInteractions(mockOperationStrategy);
    }
}