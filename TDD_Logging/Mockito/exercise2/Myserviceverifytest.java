package com.example.mockito.exercise2;

import com.example.mockito.exercise1.ExternalApi;
import com.example.mockito.exercise1.MyService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MyServiceVerifyTest {

    @Mock
    private ExternalApi mockApi;

    // ── Test 1: Verify the method was called exactly once ────────────────────

    @Test
    void testGetDataIsCalledOnce() {
        // ARRANGE
        MyService service = new MyService(mockApi);

        // ACT
        service.fetchData();   // internally calls mockApi.getData()

        // ASSERT – verify getData() was called exactly once
        verify(mockApi).getData();              // shorthand for times(1)
        verify(mockApi, times(1)).getData();    // explicit form — same result
    }

    // ── Test 2: Verify the method was NOT called ──────────────────────────────

    @Test
    void testSendDataIsNotCalledDuringFetch() {
        // ARRANGE
        MyService service = new MyService(mockApi);

        // ACT – fetchData should only call getData, not sendData
        service.fetchData();

        // ASSERT – sendData should never have been called
        verify(mockApi, never()).sendData(anyString());
    }

    // ── Test 3: Verify with specific argument ─────────────────────────────────

    @Test
    void testSendDataIsCalledWithCorrectPayload() {
        // ARRANGE
        MyService service = new MyService(mockApi);
        String payload = "Hello ExternalApi";

        // ACT
        service.pushData(payload);   // internally calls mockApi.sendData(payload)

        // ASSERT – verify sendData was called with exactly this argument
        verify(mockApi).sendData("Hello ExternalApi");
    }

    // ── Test 4: Verify call count ─────────────────────────────────────────────

    @Test
    void testGetDataCalledMultipleTimes() {
        // ARRANGE
        when(mockApi.getData()).thenReturn("Data");
        MyService service = new MyService(mockApi);

        // ACT
        service.fetchData();
        service.fetchData();
        service.fetchData();

        // ASSERT – verify exactly 3 calls
        verify(mockApi, times(3)).getData();
        verify(mockApi, atLeastOnce()).getData();
        verify(mockApi, atMost(5)).getData();
    }

    // ── Test 5: Matches the solution code from the exercise PDF ──────────────

    @Test
    void testVerifyInteraction() {
        // Solution from exercise document (programmatic style)
        ExternalApi localMockApi = Mockito.mock(ExternalApi.class);
        MyService   service      = new MyService(localMockApi);

        service.fetchData();

        verify(localMockApi).getData();   // passes if getData() was called once
    }
}
