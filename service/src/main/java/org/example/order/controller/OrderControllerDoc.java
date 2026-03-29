package org.example.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.order.dto.OrderRequest;
import org.example.order.dto.OrderResponse;
import org.example.order.valueobject.OrderStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Tag(name = "Order Controller", description = "API для управления заказами")
public interface OrderControllerDoc {

    @Operation(
            summary = "Создать новый заказ",
            description = "Создает новый заказ. Статус заказа устанавливается в CREATED."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Заказ успешно создан",
                    content = @Content(schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос"),
            @ApiResponse(responseCode = "409", description = "Заказ с таким номером уже существует")
    })
    ResponseEntity<OrderResponse> createOrder(
            @Parameter(description = "Данные для создания заказа", required = true)
            @RequestBody(description = "Запрос на создание заказа", required = true)
            OrderRequest request
    );

    @Operation(
            summary = "Получить заказ по ID",
            description = "Возвращает информацию о заказе по его UUID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ найден",
                    content = @Content(schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    ResponseEntity<OrderResponse> getOrder(
            @Parameter(description = "UUID заказа", required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id
    );

    @Operation(
            summary = "Получить все заказы",
            description = "Возвращает список всех заказов в системе"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список заказов получен",
                    content = @Content(schema = @Schema(implementation = List.class)))
    })
    ResponseEntity<List<OrderResponse>> getAllOrders();

    @Operation(
            summary = "Обновить заказ",
            description = "Полностью обновляет информацию о заказе"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ успешно обновлен",
                    content = @Content(schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    ResponseEntity<OrderResponse> updateOrder(
            @Parameter(description = "UUID заказа", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Обновленные данные заказа", required = true)
            @RequestBody(description = "Запрос на обновление заказа", required = true)
            OrderRequest request
    );

    @Operation(
            summary = "Удалить заказ",
            description = "Удаляет заказ по его UUID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Заказ успешно удален"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    ResponseEntity<Void> deleteOrder(
            @Parameter(description = "UUID заказа", required = true)
            @PathVariable UUID id
    );

    @Operation(
            summary = "Обновить статус заказа",
            description = "Изменяет статус заказа. Поддерживаемые статусы: CREATED, PAID, SHIPPED, DELIVERED, CANCELLED"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус успешно обновлен",
                    content = @Content(schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Недопустимый статус или переход"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    ResponseEntity<OrderResponse> updateStatus(
            @Parameter(description = "UUID заказа", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Новый статус заказа", required = true,
                    example = "PAID", schema = @Schema(implementation = OrderStatus.class))
            @RequestParam OrderStatus status
    );
}
