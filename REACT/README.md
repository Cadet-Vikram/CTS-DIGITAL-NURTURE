# Module 9 – React (Single Page Application Framework)
**Cognizant Digital Nurture 5.0 | Java FSE**

## Exercises Completed

| HOL | App Name | Topic | Status |
|---|---|---|---|
| 1  | myfirstreact         | Set up React, create-react-app | ✅ Mandatory |
| 2  | StudentApp           | Class components (Home/About/Contact) | ✅ Mandatory |
| 3  | scorecalculatorapp   | Functional component + CSS | ✅ Mandatory |
| 4  | blogapp              | Lifecycle: componentDidMount + Fetch API | ✅ Mandatory |
| 9  | cricketapp           | ES6: map, filter, arrow functions, destructuring, spread | ✅ Mandatory |
| 10 | officespacerentalapp | JSX: elements, attributes, inline CSS | ✅ Mandatory |
| 11 | eventexamplesapp     | Event handling, synthetic events, currency converter | ✅ Mandatory |
| 12 | ticketbookingapp     | Conditional rendering: login/logout | ✅ Mandatory |
| 13 | bloggerapp           | Lists, keys, 3 rendering techniques | ✅ Mandatory |
| 15 | ticketraisingapp     | Forms: controlled inputs + handleSubmit | ✅ Additional |
| 16 | mailregisterapp      | Form validation: name/email/password | ✅ Additional |
| 17 | fetchuserapp         | Fetch REST API from randomuser.me | ✅ Additional |

> **HOL 5** (CSS Modules) and **HOL 14** (Context API) require downloading
> attached ZIP files from the course platform. Download and run as instructed.

---

## How to Run Any App

```bash
# 1. Navigate into the app folder
cd HOL1_myfirstreact

# 2. Install dependencies (first time only)
npm install

# 3. Start the development server
npm start

# App opens at http://localhost:3000
```

---

## App Summaries

### HOL 1 – myfirstreact
Displays `"welcome to the first session of React"` as a page heading.  
Demonstrates: `create-react-app`, JSX, functional component.

### HOL 2 – StudentApp
Three **class components** (Home, About, Contact) rendered from App.  
Demonstrates: class syntax, `render()`, multiple component composition.

### HOL 3 – scorecalculatorapp
**Functional component** `CalculateScore` receives `name`, `school`, `total`, `goal` as props.  
Calculates `(total/goal) × 100` and shows Pass/Fail with color.  
Demonstrates: props, functional components, CSS Module in `Stylesheets/mystyle.css`.

### HOL 4 – blogapp
`Posts` class component fetches data from `https://jsonplaceholder.typicode.com/posts`.  
Demonstrates: `componentDidMount()`, `componentDidCatch()`, Fetch API, `setState`.

### HOL 9 – cricketapp
Two components: `ListofPlayers` and `IndianPlayers`. Toggle with `flag=true/false`.  
Demonstrates ES6 features:
- `map()` to iterate player array
- Arrow functions `p => p.score < 70`
- `filter()` to find low scorers
- Destructuring: `const { name: captainName } = players[0]`
- Spread operator: `[...t20Players, ...ranjiPlayers]`

### HOL 10 – officespacerentalapp
Renders office data using JSX elements, attributes (img src), and object display.  
Demonstrates: inline CSS, conditional color (`rent < 60000 ? 'red' : 'green'`), list rendering.

### HOL 11 – eventexamplesapp
Two components: `Counter` (increment/decrement with multiple handlers) and `CurrencyConvertor` (INR → EUR).  
Demonstrates: event binding, arrow functions in class, argument passing `() => func('arg')`, synthetic events.

### HOL 12 – ticketbookingapp
Login/Logout toggle shows `GuestPage` or `UserPage`.  
Demonstrates: 3 conditional rendering techniques: ternary, element variable, inline `&&`.

### HOL 13 – bloggerapp
Three components: `BookDetails` (element variable if-else), `BlogDetails` (ternary + &&), `CourseDetails` (null return).  
Demonstrates: `map()` with `key`, multiple conditional rendering approaches.

### HOL 15 – ticketraisingapp
`ComplaintRegister` form: employee name (textbox) + complaint (textarea).  
On submit: generates `REF-<timestamp>` reference number shown in alert + UI.

### HOL 16 – mailregisterapp
`Register` component validates in real-time (`onChange`) and on submit (`onSubmit`):
- Name ≥ 5 characters
- Email contains `@` and `.`
- Password ≥ 8 characters

### HOL 17 – fetchuserapp
`Getuser` class component calls `componentDidMount()` → fetches from `https://api.randomuser.me/`.  
Displays: user's title, first name, profile image, email, and country.
