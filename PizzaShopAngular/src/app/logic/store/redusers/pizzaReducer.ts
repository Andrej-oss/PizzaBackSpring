import {InitialPizzaState} from '../../../components/models/InitialPIzzaState';
import {PizzaAction, PizzaActionType} from '../actions-type/pizzaAction';

export const pizzaNode = 'pizzaStore';

export const pizzaReducer = (state = InitialPizzaState, action: PizzaAction) => {
  switch (action.type) {
    case PizzaActionType.pizzaAllLoaded: {
      return{
        ...state,
        pizzas: action.payload
      };
    }
    case PizzaActionType.pizzaIngredientsLoaded: {
      return {
        ...state,
        ingredients: action.payload,
      };
    }
    case PizzaActionType.pizzaSizeLoaded: {
      return {
        ...state,
        size: action.payload,
      };
    }
    case PizzaActionType.pizzaRatingLoaded: {
      return {
        ...state,
        rating: action.payload
      };
    }
    case PizzaActionType.pizzaCommentSaveLoaded: {
      return {
        ...state,
        comments: action.payload,
      };
    }
    case PizzaActionType.pizzaCommentsLoaded: {
      return {
        ...state,
        comments: action.payload,
      };
    }
    case PizzaActionType.pizzaCommentDeleteInState: {
      const { comments } = state;
      const { id } = action.payload;
      const newComments = [...comments];
      const index = newComments.findIndex(value => value.id === id);
      newComments.splice(index, 1);
      return {
        ...state,
        comments: newComments,
      };
    }
    case PizzaActionType.pizzaCommentUpdateInState: {
      const { comments } = state;
      const { id, comment } = action.payload;
      debugger;
      const newComments = [...comments];
      const index = newComments.findIndex(value => value.id === id);
      newComments.splice(index, 1, comment);
      return {
        ...state,
        comments: newComments,
      };
    }
    case PizzaActionType.pizzaSaveAndLoaded: {
      return {
        ...state,
        pizzas: action.payload,
      };
    }
    case PizzaActionType.pizzaDeleteAndLoaded: {
      return {
        ...state,
        pizzas: action.payload,
      };
    }
    case PizzaActionType.pizzaSizesLoaded: {
      return {
        ...state,
        sizes: action.payload,
      };
    }
    case PizzaActionType.promotionsLoaded: {
      return {
        ...state,
        promotions: action.payload,
      };
    }
    case PizzaActionType.drinksLoaded: {
      return {
        ...state,
        drinks: action.payload,
      };
    }
    case PizzaActionType.snacksLoaded: {
      return {
        ...state,
        snacks: action.payload,
      };
    }
    case PizzaActionType.dessertLoaded: {
      return {
        ...state,
        desserts: action.payload,
      };
    }
    default: {
      return state;
    }
  }
};
