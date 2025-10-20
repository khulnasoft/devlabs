package ksoft.seat_management_pb

import io.grpc.CallOptions
import io.grpc.CallOptions.DEFAULT
import io.grpc.Channel
import io.grpc.Metadata
import io.grpc.MethodDescriptor
import io.grpc.ServerServiceDefinition
import io.grpc.ServerServiceDefinition.builder
import io.grpc.ServiceDescriptor
import io.grpc.Status
import io.grpc.Status.UNIMPLEMENTED
import io.grpc.StatusException
import io.grpc.kotlin.AbstractCoroutineServerImpl
import io.grpc.kotlin.AbstractCoroutineStub
import io.grpc.kotlin.ClientCalls
import io.grpc.kotlin.ClientCalls.unaryRpc
import io.grpc.kotlin.ServerCalls
import io.grpc.kotlin.ServerCalls.unaryServerMethodDefinition
import io.grpc.kotlin.StubFor
import kotlin.String
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic
import ksoft.seat_management_pb.SeatManagementServiceGrpc.getServiceDescriptor

/**
 * Holder for Kotlin coroutine-based client and server APIs for
 * ksoft.seat_management_pb.SeatManagementService.
 */
public object SeatManagementServiceGrpcKt {
  public const val SERVICE_NAME: String = SeatManagementServiceGrpc.SERVICE_NAME

  @JvmStatic
  public val serviceDescriptor: ServiceDescriptor
    get() = SeatManagementServiceGrpc.getServiceDescriptor()

  public val getUserStatusMethod:
      MethodDescriptor<SeatManagement.GetUserStatusRequest, SeatManagement.GetUserStatusResponse>
    @JvmStatic
    get() = SeatManagementServiceGrpc.getGetUserStatusMethod()

  public val registerUserMethod:
      MethodDescriptor<SeatManagement.RegisterUserRequest, SeatManagement.RegisterUserResponse>
    @JvmStatic
    get() = SeatManagementServiceGrpc.getRegisterUserMethod()

  /**
   * A stub for issuing RPCs to a(n) ksoft.seat_management_pb.SeatManagementService service as
   * suspending coroutines.
   */
  @StubFor(SeatManagementServiceGrpc::class)
  public class SeatManagementServiceCoroutineStub @JvmOverloads constructor(
    channel: Channel,
    callOptions: CallOptions = DEFAULT,
  ) : AbstractCoroutineStub<SeatManagementServiceCoroutineStub>(channel, callOptions) {
    public override fun build(channel: Channel, callOptions: CallOptions):
        SeatManagementServiceCoroutineStub = SeatManagementServiceCoroutineStub(channel,
        callOptions)

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][Status].  If the RPC completes with another status, a corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun getUserStatus(request: SeatManagement.GetUserStatusRequest, headers: Metadata
        = Metadata()): SeatManagement.GetUserStatusResponse = unaryRpc(
      channel,
      SeatManagementServiceGrpc.getGetUserStatusMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][Status].  If the RPC completes with another status, a corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun registerUser(request: SeatManagement.RegisterUserRequest, headers: Metadata =
        Metadata()): SeatManagement.RegisterUserResponse = unaryRpc(
      channel,
      SeatManagementServiceGrpc.getRegisterUserMethod(),
      request,
      callOptions,
      headers
    )
  }

  /**
   * Skeletal implementation of the ksoft.seat_management_pb.SeatManagementService service based on
   * Kotlin coroutines.
   */
  public abstract class SeatManagementServiceCoroutineImplBase(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
  ) : AbstractCoroutineServerImpl(coroutineContext) {
    /**
     * Returns the response to an RPC for
     * ksoft.seat_management_pb.SeatManagementService.GetUserStatus.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun getUserStatus(request: SeatManagement.GetUserStatusRequest):
        SeatManagement.GetUserStatusResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.seat_management_pb.SeatManagementService.GetUserStatus is unimplemented"))

    /**
     * Returns the response to an RPC for
     * ksoft.seat_management_pb.SeatManagementService.RegisterUser.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun registerUser(request: SeatManagement.RegisterUserRequest):
        SeatManagement.RegisterUserResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.seat_management_pb.SeatManagementService.RegisterUser is unimplemented"))

    public final override fun bindService(): ServerServiceDefinition =
        builder(getServiceDescriptor())
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = SeatManagementServiceGrpc.getGetUserStatusMethod(),
      implementation = ::getUserStatus
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = SeatManagementServiceGrpc.getRegisterUserMethod(),
      implementation = ::registerUser
    )).build()
  }
}
