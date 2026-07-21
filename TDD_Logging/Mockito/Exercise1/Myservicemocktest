package com.example.mockito.exercise1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MyServiceMockTest {

    // ── Approach 1: Mockito.mock() — programmatic (matches exercise solution) ──

    @Test
    void testExternalApiReturnsStubbed_ProgrammaticMock() {
        // ARRANGE – create mock and stub its behaviour
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        when(mockApi.getData()).thenReturn("Mock Data");

        MyService service = new MyService(mockApi);

        // ACT
        String result = service.fetchData();

        // ASSERT
        assertEquals("Mock Data", result,
                     "Service should return exactly what the mocked API returns");
    }

    // ── Approach 2: @Mock annotation — cleaner for multiple mocks ─────────────

    @Mock
    private ExternalApi annotatedMockApi;   // injected by MockitoExtension

    @Test
    void testExternalApiReturnsStubbed_AnnotationMock() {
        // ARRANGE – stub the injected mock
        when(annotatedMockApi.getData()).thenReturn("Annotation Mock Data");

        MyService service = new MyService(annotatedMockApi);

        // ACT
        String result = service.fetchData();

        // ASSERT
        assertEquals("Annotation Mock Data", result);
    }

    @Test
    void testDefaultMockReturnIsNull() {
        // Without stubbing, a mock returns null for reference types
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        MyService service   = new MyService(mockApi);

        // No when(...).thenReturn(...) — getData() returns null by default
        String result = service.fetchData();

        assertEquals(null, result, "Un-stubbed mock should return null");
    }
}
