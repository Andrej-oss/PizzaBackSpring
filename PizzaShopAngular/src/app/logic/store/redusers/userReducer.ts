import {InitialUserState} from '../../../components/models/InitialUserState';
import {UserActions, UserActionsTypes} from '../actions-type/userActions';



export const userNode = 'userStore';
export const userReducer = (state = InitialUserState, action: UserActions) => {
  switch (action.type) {
    case UserActionsTypes.loadedUsers: {
      return {
        ...state,
        users: action.payload,
      };
    }
    case UserActionsTypes.loadedPrincipal: {
      return {
        ...state,
        principal: action.payload
      };
    }
    case UserActionsTypes.loadedCart: {
      return {
        ...state,
        cart: action.payload,
      };
    }
    case UserActionsTypes.incrementAmountPizzaCart: {
      const { cart } = state;
      const {cart1, id} = action.payload;
      const newCart = [...cart];
      const index = newCart.findIndex(value => value.id === id);
      debugger;
      newCart.splice(index, 1, cart1);
      return {
        ...state,
        cart: newCart,
      };
    }
    case UserActionsTypes.decrementAmountPizzaCart: {
      const { cart } = state;
      const {cart1, id} = action.payload;
      const newCart = [...cart];
      const index = newCart.findIndex(value => value.id === id);
      debugger;
      newCart.splice(index, 1, cart1);
      return {
        ...state,
        cart: newCart,
      };
    }
    case UserActionsTypes.deletePizzaCart: {
      const { id } = action.payload;
      const { cart } = state;
      const newCart = [...cart];
      const index = newCart.findIndex(value => value.id === id);
      newCart.splice(index, 1);
      return {
        ...state,
        cart: newCart,
      };
    }
    case UserActionsTypes.purchasesUserLoaded: {
      return {
        ...state,
        purchases: action.payload,
      };
    }
    case UserActionsTypes.deletePurchase: {
      const { purchases } = state;
      const newPurchases = [...purchases];
      const findIndex = newPurchases.findIndex(value => value.id === action.payload.id);
      newPurchases.splice(findIndex, 1);
      return {
        ...state,
        purchases: newPurchases,
      };
    }
    case UserActionsTypes.commentsUserLoaded: {
      return {
        ...state,
        comments: action.payload,
      };
    }
    case UserActionsTypes.purchasesAllLoaded: {
      return {
        ...state,
        purchases: action.payload.purchases
      };
    }
    case UserActionsTypes.avatarSaveLoaded: {
      return {
        ...state,
        avatar: action.payload,
      };
    }
    case UserActionsTypes.avatarsLoaded: {
      return {
        ...state,
        avatars: action.payload,
      };
    }
    default: {
      return state;
    }
  }
};
