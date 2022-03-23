const { Neo4jGraphQL } = require("@neo4j/graphql");
const { ApolloServer } = require("apollo-server");
const neo4j = require("neo4j-driver");

// (You may need to replace your connection details, username and password)
const AURA_ENDPOINT = "neo4j://";
const USERNAME = "";
const PASSWORD = "";

// Create Neo4j driver instance
const driver = neo4j.driver(
  AURA_ENDPOINT,
  neo4j.auth.basic(USERNAME, PASSWORD),
  { encrypted: true }
);

const typeDefs = `
  type User {
    id: ID @id
    name: String
    surname: String
    bio: String
    email: String @unique
    password: String
    imageUrl: String
    username: String
    level: Int
    interests: [Interest] @relationship(type: "IS_INTERESTED_IN", direction: OUT)
    completedPlans: [Plan] @relationship(type: "HAS_COMPLETED", direction: OUT)
    progressingPlans: [PlanInProgress] @relationship(type: "HAS_TO_COMPLETE", direction: OUT)
    token: String
  }

  type Interest {
    id: ID @id
    level: Int
    user:User @relationship(type: "IS_INTERESTED_IN", direction: IN)
    topic: Topic @relationship(type: "COUPLED_TO", direction: OUT)
  }

  type Topic {
    name: String! @unique
    imageUrl: String
  }

  type Plan {
    id: ID @id
    title: String
    subtitle: String
    imageUrl: String
    level: Int
    duration: Int
    tags: [Topic] @relationship(type: "RELATED_TO", direction: OUT)
    steps: [Step] @relationship(type: "COMPOSED_BY", direction: OUT)
  }

  type Step {
    id: ID @id
    title: String
    subtitle: String
    planWeek: Int
    plan: Plan @relationship(type: "COMPOSED_BY", direction: IN)
    materials: [Material] @relationship(type: "HAS_MATERIAL", direction: OUT)
  }

  type PlanInProgress {
    id: ID @id
    progress: Int
    endingDate: Date
    toDoSteps: [StepInProgress] @relationship(type: "HAS_LEFT", direction: OUT)
    plan: Plan @relationship(type: "REFERS_TO", direction: OUT)
    user: User @relationship(type: "HAS_TO_COMPLETE", direction: IN)
  }

  type StepInProgress {
    id: ID @id
    endDate: Date
    step: Step @relationship(type: "REFERS_TO", direction: OUT)
    plan: PlanInProgress @relationship(type: "HAS_LEFT", direction: IN)
  }

  type Material {
    id: ID @id
    title: String
    type: String
    text: String
    link: String
    description: String
    file: String
  }
`;
/*


interface Material {
  title: String
  type: String
}

type Text implements Material {
  title: String
  type: String
  text: String
}

type Spreaker implements Material {
  title: String
  type: String
  link: String
  description: String
}

type Pdf implements Material {
  title: String
  type: String
  file: String
}

type YouTube implements Material {
  title: String
  type: String
  link: String
  description: String
}

type Link implements Material {
  title: String
  type: String
  link: String
  description: String
}
*/



// const resolvers = {
//   Query: {
//     topic(parent, args, context, info) {
//       return topic.find(topic => topic.name === args.name);
//     }
//   }
// }





// Create instance that contains executable GraphQL schema from GraphQL type definitions
const neoSchema = new Neo4jGraphQL({
  typeDefs,
  driver,
  // resolvers
});

// Create ApolloServer instance to serve GraphQL schema
const server = new ApolloServer({
  schema: neoSchema.schema,
  introspection: true,
  playground: true,
});

// Start ApolloServer
server.listen().then(({ url }) => {
  console.log(`GraphQL server ready at ${url}`);
});
